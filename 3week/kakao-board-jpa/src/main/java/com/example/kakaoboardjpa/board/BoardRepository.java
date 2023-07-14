package com.example.kakaoboardjpa.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("select b from Board b join b.user where b.id = :id")
    Optional<Board> mFindByIdJoinUser(@Param("id") int id);

    @Query("select b from Board b join fetch b.user where b.id = :id")
    Optional<Board> mFindByIdJoinFetchUser(@Param("id") int id);

}
