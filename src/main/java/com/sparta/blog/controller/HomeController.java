package com.sparta.blog.controller;

import com.sparta.blog.dto.UserInfoDto;
import com.sparta.blog.model.UserRoleEnum;
import com.sparta.blog.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @PostMapping("/home")
    public ModelAndView home(@RequestBody UserInfoDto userInfoDto) {
        ModelAndView model = new ModelAndView();
        model.addObject("username" , userInfoDto.getUsername());
        model.addObject("userid" , userInfoDto.getId());
        model.setViewName("index");
        return model;
    }
}