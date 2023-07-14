package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    public List<Item> findAllByOrderId(int orderId);

    @Query("SELECT i FROM Item i JOIN FETCH i.order JOIN FETCH i.option o JOIN FETCH o.product WHERE i.order.user.id=:userId")
    public List<Item> mFindAllByUserId(int userId);

    @Query("SELECT i FROM Item i JOIN FETCH i.order JOIN FETCH i.option o JOIN FETCH o.product WHERE i.order.id=:orderId")
    public List<Item> mFindAllByOrderId(int orderId);

}
