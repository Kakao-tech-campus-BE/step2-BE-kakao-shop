package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select count(*)" +
            "from Order o " +
            "where o.user.id = :user_id")
    int countByUserId(@Param("user_id") int user_id);


    @Query("select o " +
            "from Order o " +
            "join fetch o.user " +
            "where o.user.id = :user_id ")
    List<Order> findByUserId(@Param("user_id") int userId);
}