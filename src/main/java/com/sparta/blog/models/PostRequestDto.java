package com.sparta.blog.models;


import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String autor;
    private String content;
    private String password;
}
