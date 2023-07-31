package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
    @Query("select Count(*) " +
            "from Order o " +
            "where o.user.id = :userId")
    int countOrderIdByUserId(@Param("userId") int userId);

    @Query(value = "select o " +
            "from Order o " +
            "where o.user.id = :userId ")
    List<Order> findAllOrderByUserIdAndIndex(@Param("userId") int id);

}
