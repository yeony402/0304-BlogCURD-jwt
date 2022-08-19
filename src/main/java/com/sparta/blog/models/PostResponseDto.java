package com.sparta.blog.models;


import com.sparta.blog.domain.Post;
import lombok.Getter;

import java.util.Optional;

@Getter
public class PostResponseDto {
    private String password;

    public PostResponseDto(Optional<Post> post) {
        this.password = post.get().getPassword();
    }

}