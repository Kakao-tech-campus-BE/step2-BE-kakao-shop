package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    // 결제하기2 - 주문아이디로 아이템인스턴스를 받아온다.
    List<Item> findByOrderId(int orderId);


}
