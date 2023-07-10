package com.sparta.board.entity;

import com.sparta.board.dto.CmtRequestDto;
import jakarta.persistence.*;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board")
    private Board board;

    public Cmt(CmtRequestDto cmtRequestDto, String username) {
        this.postid = cmtRequestDto.getPostid();
        this.comment = cmtRequestDto.getComment();
    }

    public Cmt(Cmt cmt) {
        this.postid=cmt.getPostid();
        this.username=cmt.getUsername();
        this.comment= cmt.username;
    }

    public void update(CmtRequestDto cmtRequestDto) {
        this.comment = cmtRequestDto.getComment();
    }
}
