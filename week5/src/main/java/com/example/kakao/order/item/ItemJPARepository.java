package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    // 쿼리 최적화 전
    // where 문에서 테이블 참조를 3번이나 하는데 이렇게 참조를 많이 해도 성능상에 괜찮은가 ??
    @Query("select i from Item i where i.order.id = :orderId and i.order.user.id = :userId")
    List<Item> findByOrderIdAndUserId(@Param("orderId") int orderId, @Param("userId") int userId);
}
