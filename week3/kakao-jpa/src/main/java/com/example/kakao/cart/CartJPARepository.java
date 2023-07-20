package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c " +
            " join fetch c.option o " +
            " join fetch o.product " +
            " where c.user.id = :userId")
    List<Cart> mFindByUserId(@Param("userId") int userId);

    void deleteByUserId(@Param("userId") int userId);

}