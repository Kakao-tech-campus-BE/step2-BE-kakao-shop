package com.example.kakao.order;

import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {


    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.user.id = :userId")
    List<Order> findByUserIdWithJoinFetch(@Param("userId") int userId);


    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.id = :id")
    Order findByUserAndId(@Param("user") User user, @Param("id") int id);

}
