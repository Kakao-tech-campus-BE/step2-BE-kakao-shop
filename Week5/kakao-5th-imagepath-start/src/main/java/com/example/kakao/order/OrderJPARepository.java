package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.id = :itemId and o.user.id = :userId")
    Optional<Order> findByIdAndUserId(@Param("itemId") int itemId, @Param("userId") int userId);
}
