package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
<<<<<<< HEAD

    // 쿼리 수정 필요
    List<Item> findAllByOrderId(int orderId);
}
=======
    List<Item> findAllByOrderId(int orderId);
}
>>>>>>> f44b51781d427fceba3fc6d747ac84e97177f447
