package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i " +
            "join fetch i.option o " +
            "join fetch i.order r " +
            "join fetch o.product p " +
            "join fetch r.user u " +
            "where r.id = :orderId")
    List<Item> findByOrderId(int orderId);

    @Query("select i from Item i " +
            "join fetch i.option o " +
            "join fetch i.order r " +
            "join fetch o.product p " +
            "join fetch r.user u " +
            "where u.id = :userId")
    List<Item> findByUserId(int userId);

    @Query("select i from Item i " +
            "join fetch i.option o " +
            "join fetch i.order r " +
            "join fetch o.product p " +
            "join fetch r.user u " +
            "where p.id = :productId")
    List<Item> findByProductId(int productId);

    @Query("select i from Item i " +
            "join fetch i.option o " +
            "join fetch i.order r " +
            "join fetch r.user u " +
            "where o.id = :optionId")
    List<Item> findByOptionId(int optionId);
}
