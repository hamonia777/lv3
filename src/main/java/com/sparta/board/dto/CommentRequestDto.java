package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long post_id;
    private String comment;

}
