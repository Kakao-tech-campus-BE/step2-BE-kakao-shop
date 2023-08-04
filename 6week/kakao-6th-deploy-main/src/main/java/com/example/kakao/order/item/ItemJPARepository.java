package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i join fetch i.option o join fetch o.product p where i.order.id = :id")
    List<Item> findAllByOrder(int id);
}
