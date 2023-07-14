package com.example.kakao.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByUserId(int id);
}
