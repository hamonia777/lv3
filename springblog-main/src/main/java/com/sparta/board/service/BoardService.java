package com.sparta.board.service;

import com.sparta.board.dto.DeleteDto;
import com.sparta.board.dto.MessageResponseDto;
import com.sparta.board.dto.PostRequestDto;
import com.sparta.board.dto.PostResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Cmt;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CmtRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final CmtRepository cmtRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
//        String username=jwtUtil.getUsername();
        String username=requestDto.getUsername();
//        if(!username.equals(requestDto.getUsername())){
//            throw new IllegalArgumentException("사용자명이 일치하지 않습니다.");
//        }
        Board board = new Board(requestDto);
        Board savePost = boardRepository.save(board);
        return new PostResponseDto(savePost);
    }
     public List<PostResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
     @Transactional
     public PostResponseDto getPost(Long id) {
        Board board = findPost(id);
        return new PostResponseDto(board);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest req) {
        Board board = findPost(id);
        board.update(requestDto);
        return new PostResponseDto(board);
    }

    public MessageResponseDto deletePost(Long id, PostRequestDto requestDto, HttpServletRequest req) {
        Board board = findPost(id);
        boardRepository.delete(board);
        return new MessageResponseDto("삭제 완료", HttpStatus.OK.value());
    }

    private Board findPost(Long id){
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }

//    private void validateTokenAndUser(PostRequestDto requestDto, HttpServletRequest req){
//        String token = jwtUtil.getJwtFromHeader(req);
//        if(!jwtUtil.validateToken(token)){
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }
//        Claims claims = jwtUtil.getUserInfoFromToken(token);
//        String username = claims.getSubject();
//        if(!username.equals(requestDto.getUsername())){
//            throw new IllegalArgumentException("사용자명이 일치하지 않습니다.");
//        }
//    }

    public List<Cmt> getcmt(Long id) {
        return cmtRepository.findAllBypostid(id);
    }

}
