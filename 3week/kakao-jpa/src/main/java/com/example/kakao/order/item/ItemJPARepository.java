package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    List<Item> findByOrderId(@Param("orderId") int orderId);



    /*
        주문 결과 확인에서 item, order, option, product 테이블 모두 필요하므로
        fetch join으로 Item 테이블에서 orderId를 @Param으로 받아서 inner join으로
        Item + Option + product 테이블 들고 오기
     */

    @Query("select i from Item i join fetch i.option p join fetch p.product where i.order.id = :orderId ")
    List<Item> mfindByOrderId(@Param("orderId") int orderId);

}
