package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionJPARepository extends JpaRepository<Option, Integer> {
    List<Option> findByProductId(@Param("productId") int productId);
}
