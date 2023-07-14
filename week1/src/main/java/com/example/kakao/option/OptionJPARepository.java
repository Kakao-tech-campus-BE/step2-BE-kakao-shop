package com.example.kakao.option;

import com.example.kakao.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionJPARepository extends JpaRepository<Option, Integer> {
    List<Option> findByProduct(Product product);
}
