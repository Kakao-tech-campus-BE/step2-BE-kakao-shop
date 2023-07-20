package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.option where c.option.id = :optionId")
    List<Cart> mFindByOptionId(@Param("optionId") int optionId);

    List<Cart> findAllByUserId(int id);

    @Query("select c from Cart c join fetch c.user where c.user.id = :userId")
    List<Cart> mFindByUserId(int userId);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = :quantity, c.price = :price WHERE c.id = :id")
    int updateCartById(@Param("id") int id, @Param("quantity") int quantity, @Param("price") int price);
}
