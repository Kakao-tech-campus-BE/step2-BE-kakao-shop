package com.example.kakao.order.item;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i where i.order.user= :user")
    List<Item> findByUserId(@Param("user") User user);


    @Query("select i from Item i join fetch i.order where i.order.id = :order")
    List<Item> findByOrder(@Param("order") int order);


}
