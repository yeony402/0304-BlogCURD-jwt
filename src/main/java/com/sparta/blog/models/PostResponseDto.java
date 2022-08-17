package com.sparta.blog.models;


import com.sparta.blog.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private String password;

    public PostResponseDto(Post entity) {
        this.password = entity.getPassword();
    }
}