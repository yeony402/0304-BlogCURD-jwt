package com.sparta.blog.dto;


import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String autor;
    private String content;
    private String password;
}
