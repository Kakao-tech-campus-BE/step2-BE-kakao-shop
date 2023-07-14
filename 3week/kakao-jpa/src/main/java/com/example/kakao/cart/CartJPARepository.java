package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c " +
            "JOIN FETCH c.user u " +
            "JOIN FETCH c.option o " +
            "JOIN FETCH o.product " +
            "WHERE u.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.id = :id")
    void updateQuantityById(@Param("id") int id, @Param("quantity") int quantity);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId")int userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = :id")
    void deleteById(@Param("id")int id);

}
