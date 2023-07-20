package com.example.kakao.order;

import com.example.kakao.order.item.Item;
import com.example.kakao.product.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    // List<Option> findByProductId(@Param("productId") int productId);
    // Optional<Option> findById(int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select i from Item i join fetch i.order where i.order.id = :orderId")
    List<Item> mFindByOrderId(@Param("orderId") int orderId);
}
