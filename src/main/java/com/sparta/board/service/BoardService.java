package com.sparta.board.service;

import com.sparta.board.dto.PostRequestDto;
import com.sparta.board.dto.PostResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest req) {
        User user = userRepository.findByUsername(jwtUtil.findUsername(req)).get();
        Board board = new Board(requestDto, user);
        Board savePost = boardRepository.save(board);
        return new PostResponseDto(savePost);
    }

    @Transactional
    public List<PostResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Board board = findPost(id);
        return new PostResponseDto(board);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest req) {
        Board board = findPost(id);
        User user = new User();
        if(!(user.getRole() == UserRoleEnum.ADMIN)){
            jwtUtil.validateTokenAndUser(req, user);
        }
        board.update(requestDto);
        return new PostResponseDto(board);
    }

    public void deletePost(Long id, HttpServletRequest req) {
        Board board = findPost(id);
        User user = new User();
        if(!(user.getRole() == UserRoleEnum.ADMIN)){
            jwtUtil.validateTokenAndUser(req, user);
        }
        boardRepository.delete(board);
    }

    private Board findPost(Long id){
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }

}
