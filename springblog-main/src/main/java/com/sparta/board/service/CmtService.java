package com.sparta.board.service;

import com.sparta.board.dto.CmtRequestDto;
import com.sparta.board.dto.CmtResponseDto;
import com.sparta.board.dto.PostResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Cmt;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CmtRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CmtService {
    private final CmtRepository cmtRepository;
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;

    @Transactional
    public CmtResponseDto createcmt(CmtRequestDto cmtRequestDto) {
        String username = "";
        Long id = cmtRequestDto.getPostid();
         Cmt cmt = new Cmt(cmtRequestDto, username);
        Cmt savecmt = cmtRepository.save(cmt);
        return new CmtResponseDto(savecmt);
    }

    public PostResponseDto insertcmt(Long id){
        Board board = findPost(id);
        List<Cmt> updatedCommentList = getcmt(id);
        board.update(updatedCommentList);
        PostResponseDto responseDto =new PostResponseDto(board);
        return responseDto;
    }
    public List<Cmt> getcmt(Long id) {
        return cmtRepository.findAllBypostid(id);
    }
     public CmtResponseDto updatecmt(Long id, CmtRequestDto cmtRequestDto) {
        Cmt cmt = findcmt(id);
        cmt.update(cmtRequestDto);
        return new CmtResponseDto(cmt);
    }
     public void deletecmt(Long id) {
        Cmt cmt =findcmt(id);
        cmtRepository.delete(cmt);
    }

    private Cmt findcmt(Long id){
        return  cmtRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
    }
    private Board findPost(Long id){
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }


}
