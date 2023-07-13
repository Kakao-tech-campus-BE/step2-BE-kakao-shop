package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN FETCH c.user u JOIN FETCH c.option o JOIN FETCH o.product WHERE u.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);

    @Modifying  // UPDATE, DELETE에는 @Modifying을 붙여야 한다! - 에러 Not supported for DML operations
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.id = :id")
    void updateQuantityById(@Param("id") int id, @Param("quantity") int quantity);

    Cart findById(int id);
}
