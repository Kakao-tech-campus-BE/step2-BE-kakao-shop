package com.example.kakao.order;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {
}
