package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserId(int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.option WHERE c.user.id = :userId")
    List<Cart> mFindAllByUserId(int userId);

    List<Cart> findAllByUserIdAndOptionIdIn(int userId, List<Integer> optionId);

    @Query("SELECT c " +
            "FROM Cart c JOIN FETCH c.option " +
            "WHERE c.user.id=:userId AND c.option.id IN (:optionId)")
    List<Cart> mFindAllByUserIdAndOptionIdIn(@Param("userId") int userId, @Param("optionId") List<Integer> optionId);
}
