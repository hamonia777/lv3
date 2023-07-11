package com.sparta.board.service;

import com.sparta.board.entity.User;
import com.sparta.board.dto.MessageResponseDto;
import com.sparta.board.dto.PostRequestDto;
import com.sparta.board.dto.PostResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Cmt;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CmtRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final CmtRepository cmtRepository;
    private final UserRepository userRepository;


    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest req) {
        String username= jwtUtil.getUsername();
        Board board = new Board(requestDto,username);
        Board savePost = boardRepository.save(board);
        return new PostResponseDto(savePost);
    }

    @Transactional
     public List<PostResponseDto> getPosts() {
        for(Long i=1L;i<= boardRepository.count();i++){
            Board board = findPost(i);
            List<Cmt> updatedCommentList = getcmt(i);
            board.update(updatedCommentList);
        }
        return boardRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
    public List<Cmt> getcmt(Long id) {
        return cmtRepository.findAllBypostid(id);
    }
    @Transactional
    public PostResponseDto getPost(Long id) {
        Board board = findPost(id);
        List<Cmt> updatedCommentList = getcmt(id);
        board.update(updatedCommentList);
        return new PostResponseDto(board);
    }

    @Transactional
    public MessageResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest req) {
        Board board = findPost(id);
        Optional <User> user=userRepository.findByUsername(jwtUtil.getUsername());
        UserRoleEnum role=user.get().getRole();
        if((jwtUtil.getUsername().equals(board.getUsername()))||role.equals(UserRoleEnum.ADMIN) ){
            board.update(requestDto);
            return new MessageResponseDto("수정 완료", HttpStatus.OK.value());
        }
        else{
            return new MessageResponseDto("수정 실패", HttpStatus.EXPECTATION_FAILED.value());
        }
    }

    public MessageResponseDto deletePost(Long id) {
        Board board = findPost(id);
        Optional <User> user=userRepository.findByUsername(jwtUtil.getUsername());
        UserRoleEnum role=user.get().getRole();
        if((jwtUtil.getUsername().equals(board.getUsername()))||role.equals(UserRoleEnum.ADMIN) ){
        boardRepository.delete(board);
        return new MessageResponseDto("삭제 완료", HttpStatus.OK.value());
        }
        else{
            return new MessageResponseDto("삭제 실패", HttpStatus.EXPECTATION_FAILED.value());
        }
    }

    private Board findPost(Long id){
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }


}
