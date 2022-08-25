package com.sparta.blog.service;


import com.sparta.blog.model.Post;
import com.sparta.blog.repository.PostRepository;
import com.sparta.blog.dto.IdPostResponseDto;
import com.sparta.blog.dto.PasswordDto;
import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성
@Service // 서비스임을 선언
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(PostRequestDto requestDto) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Post post = new Post(requestDto);

        postRepository.save(post);

        return post;
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        post.update(requestDto);
        return post.getId();
    }

    // 입력 받은 password와 DB의 password를 비교해서 반환
    public Boolean updatePassword(Long id, PasswordDto passwordDto) {
        Optional<Post> post = postRepository.findById(id);
        if(post.get().getPassword().equals(passwordDto.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    // repository에서 password 값만 담아서 반환
    @Transactional
    public PostResponseDto findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return new PostResponseDto(post);
    }

    // 클릭된 id가 쓴 게시글 return
    @Transactional
    public IdPostResponseDto getIdPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return new IdPostResponseDto(post);
    }

    @Transactional
    public Boolean sameId(Long id, Long userId) {
        if (id == userId){
            return true;
        } else {
            return false;
        }
    }

}
