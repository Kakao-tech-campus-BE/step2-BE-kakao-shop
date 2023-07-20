package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c " +
            "join fetch c.user u " +
            "join fetch c.option o " +
            "join fetch o.product p " +
            "where u.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);

    @Query("select c from Cart c " +
            "join fetch c.option o " +
            "where c.id in :cartIds")
    List<Cart> findByIdIn(List<Integer> cartIds);

}
