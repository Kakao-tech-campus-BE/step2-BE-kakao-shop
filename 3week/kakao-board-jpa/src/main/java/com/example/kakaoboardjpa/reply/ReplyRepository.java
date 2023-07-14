package com.example.kakaoboardjpa.reply;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    @Query("select r from Reply r join fetch r.user")
    List<Reply> mFindByBoardId(@Param("boardId") int boardId, Pageable pageable);

    List<Reply> findByBoardId(@Param("boardId") int boardId);
}
