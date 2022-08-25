package com.sparta.blog.dto;

import com.sparta.blog.model.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class IdPostResponseDto {
    private Long id;
    private String title;
    private String autor;
    private String content;
    private LocalDateTime createAt;


    public IdPostResponseDto(Optional<Post> post) {
        this.id = post.get().getId();
        this.title = post.get().getTitle();
        this.autor = post.get().getAutor();
        this.content = post.get().getContent();
        this.createAt = post.get().getCreatedAt();
    }
}
