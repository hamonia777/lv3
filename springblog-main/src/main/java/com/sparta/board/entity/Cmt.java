package com.sparta.board.entity;

import com.sparta.board.dto.CmtRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cmt extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmt_num;
    private Long postid;
    private String username;
    private String comment;

    public Cmt(CmtRequestDto cmtRequestDto, String username) {
        this.postid = cmtRequestDto.getPostid();
        this.comment = cmtRequestDto.getComment();
    }

    public void update(CmtRequestDto cmtRequestDto) {
        this.comment = cmtRequestDto.getComment();
    }
}
