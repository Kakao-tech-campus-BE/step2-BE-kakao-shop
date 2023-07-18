package com.example.kakao.order.item;

import com.example.kakao.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i join fetch i.option op join fetch op.product p join fetch i.order o join fetch  o.user u where i.order.id = :orderId")
    List<Item> mFindAllByOrderId(@Param("orderId") int orderId);
    Optional<Item> findByOrderId(int orderId);
    List<Item> findAllByOrderId(int orderId);
}
