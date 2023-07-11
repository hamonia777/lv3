package com.sparta.board.entity;

import com.sparta.board.dto.CmtRequestDto;
import com.sparta.board.dto.CmtResponseDto;
import com.sparta.board.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board")
@NoArgsConstructor
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Cmt> commentlist=new ArrayList<>();
    public List<Cmt> getCommentlist(){
        return this.commentlist;
    }
    public Board(PostRequestDto requestDto,String username) {
        this.username = username;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
    public void addCmt(Cmt cmt) {
        if (this.commentlist == null) {
            this.commentlist = new ArrayList<>();
        }
        this.commentlist.add(cmt);
    }
    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
    public void update(List<Cmt> commentlist){
        this.commentlist=commentlist;
    }
}
