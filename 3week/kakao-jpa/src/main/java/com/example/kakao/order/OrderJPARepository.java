package com.example.kakao.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

  List<Order> findByUserId(@Param("userId") int userId);
}
