package com.sparta.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class MessageResponseDto {
    private String msg;
    private int statusCode;
}
