package com.sparta.blog.dto;


import com.sparta.blog.model.Post;
import lombok.Getter;

import java.util.Optional;

@Getter
public class PostResponseDto {
    private String password;

    public PostResponseDto(Optional<Post> post) {
        this.password = post.get().getPassword();
    }

}