package com.sparta.board.repository;

import com.sparta.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CommentRepository extends JpaRepository<Comment,Long> {
        // 게시글, 유저에 맞게 나와야 함 - how?
    List<Comment> findAllByBoardId(Long id);
}
