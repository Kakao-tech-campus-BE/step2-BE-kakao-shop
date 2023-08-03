package com.example.kakao.order.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemJPARepository extends JpaRepository<Item, Integer> {
    //order, option, product를 참조하기 위해 driving table을 item으로 하여 join fetch한다.
    @Query("select i from Item i join fetch i.order join fetch i.option o join fetch o.product where i.order.id = :orderId")
    List<Item> findByOrderId(@Param("orderId") int orderId);

    //그 유저의 가장 최근 주문내역을 뽑아내기 위한 쿼리
    @Query("select i from Item i join fetch i.order ord join fetch i.option op join fetch op.product join ord.user where ord.user.id = :userId order by ord.id desc")
    List<Item> findByUserIdOrderByOrderIdLimitN(@Param("userId") int userId, Pageable pageable);
}
