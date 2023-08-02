package com.example.kakao.product;

import com.example.kakao.order.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {

}
