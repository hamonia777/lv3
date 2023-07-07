package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CmtRequestDto {

    private Long cmt_num;
    private Long postid;
    private String comment;

}
