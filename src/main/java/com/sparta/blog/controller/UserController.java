package com.sparta.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.dto.UserInfoDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    // 회원정보 확인
    @ResponseBody
    @PostMapping("/user/login")
    public ResponseEntity<HttpStatus> loginUser(@RequestBody UserInfoDto userInfoDto, HttpServletResponse response, Authentication authentication) {
        return userService.LoginUser(userInfoDto, response, authentication);
    }



    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);
        return "redirect:/";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/login";
    }


}