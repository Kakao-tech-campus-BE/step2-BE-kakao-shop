package com.example.kakao.order.item;

import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i join fetch i.order where i.order.id = :orderId")
    List<Item> mFindByOrderId(@Param("orderId") int orderId);
}
