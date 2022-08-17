package com.sparta.blog.service;


import com.sparta.blog.domain.Post;
import com.sparta.blog.domain.PostRepository;
import com.sparta.blog.models.PasswordDto;
import com.sparta.blog.models.PostRequestDto;
import com.sparta.blog.models.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성
@Service // 서비스임을 선언
public class PostService {
    private final PostRepository postRepository;

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        post.update(requestDto);
        return post.getId();
    }

    // 입력 받은 password와 DB의 password를 비교해서 반환
    public Boolean updatePassword(Long id, PasswordDto requestDto) {
        Optional<Post> post = postRepository.findById(id);
        if(post.get().getPassword().equals(requestDto.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id =" +id));
        return new PostResponseDto(entity);
    }

}
