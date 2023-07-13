package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c " +
            "join fetch c.user " +
            "where c.id = :userId")
    List<Cart> readCartById(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.id in :userId")
    void deleteCartById(@Param("userId") int userId);
}