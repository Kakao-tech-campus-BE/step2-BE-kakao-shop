package com.example.kakao.item.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByOrderId(int id);
}
