package com.example.kakao.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(@Param("userId")int userId);

    @Modifying
    @Query("select o from Order o join fetch o.user  where o.user.id = :userId")
    List<Order> mFindByUserId(@Param("userId")int userId);
}
