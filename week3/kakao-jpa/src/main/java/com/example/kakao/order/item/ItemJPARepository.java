package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i join fetch i.option")
    List<Item> mFindAll();
}
