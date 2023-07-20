package com.example.kakao.order;

import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(@Param("userId") int userId);

    @Query("select o from Order o join fetch o.user u where u.email = :email")
    List<Order> findByEmail(@Param("email") String email);

}
