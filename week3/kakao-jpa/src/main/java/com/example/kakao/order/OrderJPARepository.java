package com.example.kakao.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

//    @EntityGraph(attributePaths = "user")
//    @Query("select o from Order o")
    @Query("select o from Order o " +
            "join fetch o.user u " +
            "where u.id = :userId")
    List<Order> findByUserId(int userId);
}
