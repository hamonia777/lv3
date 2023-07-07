package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Cmt;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CmtResponseDto {

    private Long postid;
    private Long cmt_num;
    private String comment;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CmtResponseDto(Cmt savecmt) {
        this.postid= savecmt.getPostid();
        this.cmt_num=savecmt.getCmt_num();
        this.comment= savecmt.getComment();
        this.username= savecmt.getUsername();
        this.createdAt=savecmt.getCreatedAt();
        this.modifiedAt=savecmt.getModifiedAt();
    }
}
