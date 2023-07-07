package com.sparta.board.repository;

import com.sparta.board.entity.Cmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CmtRepository extends JpaRepository<Cmt, Long> {
    List<Cmt> findAllByOrderByModifiedAtDesc();
    List<Cmt> findAllBypostid(Long postid);

}