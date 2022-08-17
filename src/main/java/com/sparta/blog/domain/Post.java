package com.sparta.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.blog.entity.Timestamped;
import com.sparta.blog.models.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;


@Getter // get 함수를 일괄적으로 만들어 줌
@NoArgsConstructor // 기본 생성자를 만들어 줌
@Entity // DB 테이블 역할
public class Post extends Timestamped {
    // ID 자동 증가
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String content;

    @JsonIgnore //json으로 반환될 때 해당 데이터를 숨김
    @Column(nullable = false)
    private String password;

    // 기본 생성자
    public Post(String title, String autor, String content, String password){
        this.title = title;
        this.autor = autor;
        this.content = content;
        this.password = password;
    }

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.autor = requestDto.getAutor();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.autor = requestDto.getAutor();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }


}
