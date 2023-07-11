package com.sparta.board.controller;

import com.sparta.board.dto.MessageResponseDto;
import com.sparta.board.dto.PostRequestDto;
import com.sparta.board.dto.PostResponseDto;
import com.sparta.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //html 따로 반환하지 않기 때문에 RestController 사용
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/post") // 게시글 작성
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest req) {
        PostResponseDto responseDto = boardService.createPost(requestDto, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/posts") //전체 게시글 조회
    public List<PostResponseDto> getPosts(){
        return boardService.getPosts();
    }

    @GetMapping("/post/{id}") // 선택 게시글 조회
    public PostResponseDto getPost(@PathVariable Long id){
        return boardService.getPost(id);
    }

    @PutMapping("/post/{id}") // 게시글 수정
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest req){
        PostResponseDto responseDto = boardService.updatePost(id, requestDto, req);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/post/{id}") //게시글 삭제
    public ResponseEntity<MessageResponseDto> deletePost(@PathVariable Long id, HttpServletRequest req){
        boardService.deletePost(id, req);
        return ResponseEntity.ok().body(new MessageResponseDto("삭제 완료", HttpStatus.OK.value()));
    }

}
