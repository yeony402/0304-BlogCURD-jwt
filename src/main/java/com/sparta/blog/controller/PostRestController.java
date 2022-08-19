package com.sparta.blog.controller;


import com.sparta.blog.domain.Post;
import com.sparta.blog.domain.PostRepository;
import com.sparta.blog.models.IdPostResponseDto;
import com.sparta.blog.models.PasswordDto;
import com.sparta.blog.models.PostRequestDto;
import com.sparta.blog.models.PostResponseDto;
import com.sparta.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostRestController {
    private final PostRepository postRepository;
    private final PostService postService;

    // 전체 게시글 조회
    @GetMapping("/api/posts")
    public List<Post> getPost() {
        return postRepository.findAll();
    }


    // id값으로 조회해서 id에 해당하는 데이터만 반환(password)
    @GetMapping("/api/posts/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }


    // 상세 페이지 조회
    // 타임리프, 템플릿 이동
    @RequestMapping(value = "/api/detail/{id}", method = RequestMethod.GET)
    public ModelAndView getIdPost(@PathVariable Long id) {
        ModelAndView model = new ModelAndView();
        model.addObject("data" , postService.getIdPost(id));
        model.setViewName("listone");
        return model;
    }

    // 상세 페이지 조회
//    @GetMapping("/api/detail/{id}")
//    public IdPostResponseDto getIdPost(@PathVariable Long id) {
//        return postService.getIdPost(id);
//    }

    // 게시글 등록
    @ResponseBody
    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto){
        try {
            Post post = new Post(requestDto);
            postRepository.save(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity<>("데이터가 잘못됐습니다.", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    // 비밀번호 확인
    // @ResponseBody - 클라이언트에 response값 반환
    @ResponseBody
    @PostMapping("/api/posts/{id}")
    public Boolean checkPassword(@PathVariable Long id, @RequestBody PasswordDto passwordDto){
        boolean result;
        Optional<Post> post = postRepository.findById(id);
        result = postService.updatePassword(id, passwordDto);
        return result;
    }


    // 게시글 수정
    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }
}
