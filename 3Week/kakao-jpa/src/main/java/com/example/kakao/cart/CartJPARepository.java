package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.user where c.user.id = :userId")
    List<Cart> mFindAllByUserId(@Param("userId") int userId);

    @Modifying
    @Query("update Cart c set c.quantity = :quantity, c.price = :price where c.id = :id")
    void updateColumsById(@Param("id") int id, @Param("quantity") int quantity, @Param("price") int price);

}
