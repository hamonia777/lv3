package com.sparta.board.controller;

import com.sparta.board.dto.*;
import com.sparta.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest req){
        CommentResponseDto responseDto = commentService.createComment(requestDto, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/comment/{cmt_num}")
    public ResponseEntity<CommentResponseDto> updatePost(@PathVariable Long cmt_num, @RequestBody CommentRequestDto requestDto, HttpServletRequest req){
        CommentResponseDto responseDto = commentService.updateComment(cmt_num, requestDto, req);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/comment/{cmt_num}") //게시글 삭제
    public ResponseEntity<MessageResponseDto> deletePost(@PathVariable Long cmt_num, HttpServletRequest req){
        commentService.deleteComment(cmt_num, req);
        return ResponseEntity.ok().body(new MessageResponseDto("삭제 완료", HttpStatus.OK.value()));
    }


}
