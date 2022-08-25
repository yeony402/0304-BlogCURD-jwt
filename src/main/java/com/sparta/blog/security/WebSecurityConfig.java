package com.sparta.blog.security;

import com.sparta.blog.security.filter.JwtAuthFilter;
import com.sparta.blog.security.jwt.HeaderTokenExtractor;
import com.sparta.blog.security.jwt.JwtTokenUtils;
import com.sparta.blog.security.provider.JWTAuthProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    public WebSecurityConfig(
            JWTAuthProvider jwtAuthProvider,
            HeaderTokenExtractor headerTokenExtractor
    ) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.headerTokenExtractor = headerTokenExtractor;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(jwtAuthProvider);
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        // post 수행
        http
                .cors()
                .and()
                .csrf().disable()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/", "/login/**","/user/**", "/api/posts/**", "/api/details/**", "/images/**", "/css/**", "/home", "/api/auth/**")
                .permitAll()
                .antMatchers("/css/**", "/js/**", "/img/**", "**/favicon.ico", "/favicon.ico")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                // [로그아웃 기능]
                .logout().clearAuthentication(true)
                // 로그아웃 요청 처리 URL
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling();



        // 서버에서 인증은 JWT로 인증하기 때문에 Session의 생성을 막습니다.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.addFilterBefore(new CustomAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);


    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");

        // h2-console 허용
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");

        // 회원 관리 API 허용
        skipPathList.add("GET,/user/**");
        skipPathList.add("POST,/user/signup");
        skipPathList.add("POST,/user/login");
        skipPathList.add("GET, /user/auth/**");
        skipPathList.add("POST, /user/auth/**");
        skipPathList.add("GET,/login");
        skipPathList.add("GET,/api/details/**");
        skipPathList.add("GET,/api/posts/**");
        skipPathList.add("POST,/api/posts/**");

        skipPathList.add("PUT,/api/auth/**");
        skipPathList.add("DELETE,/api/auth/**");

        skipPathList.add("GET,/");
        skipPathList.add("GET,/basic.js");

        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}