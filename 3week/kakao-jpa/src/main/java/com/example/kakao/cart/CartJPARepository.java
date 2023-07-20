package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);


    @Query("SELECT c FROM Cart c JOIN fetch c.option o JOIN fetch c.user u JOIN fetch o.product p WHERE c.user.id = :userId")
    List<Cart> mfindByUserId(@Param("userId") int userId);


}
