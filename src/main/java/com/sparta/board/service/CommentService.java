package com.sparta.board.service;


import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest req) {
        Board board = boardRepository.findById(requestDto.getPost_id()).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findByUsername(jwtUtil.findUsername(req)).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        Comment comment = new Comment(requestDto, user, board);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest req) {
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        User user = userRepository.findByUsername(jwtUtil.findUsername(req)).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        if(!(user.getRole() == UserRoleEnum.ADMIN)){
            if(!user.getUsername().equals(comment.getUser().getUsername())){
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long id, HttpServletRequest req){
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        User user = userRepository.findByUsername(jwtUtil.findUsername(req)).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
        if(!(user.getRole() == UserRoleEnum.ADMIN)){
            if(!user.getUsername().equals(comment.getUser().getUsername())){
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }
        }
        commentRepository.delete(comment);
    }
}
