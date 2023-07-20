package com.example.kakao.order.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i join fetch i.option where i.order.id = :orderId") // fetch join은 이중으로 걸 수없다. 그러니까 i.option.product 이런건 안된다.
    List<Item> mFindByOrderIdJoinFetchUser(@Param("orderId") int orderId);

    List<Item> findByOrderId(int orderId);
}
