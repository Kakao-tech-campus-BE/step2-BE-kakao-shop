package com.example.kakao.product;

import com.example.kakao.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {
    Product findByOptions(Option option);
}
