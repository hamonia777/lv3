package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmt_num;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Board board;

    public Comment(CommentRequestDto requestDto, User user, Board board){
        this.content = requestDto.getContent();
        this.user = user;
        this.board = board;
    }

    public void update(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }
}
