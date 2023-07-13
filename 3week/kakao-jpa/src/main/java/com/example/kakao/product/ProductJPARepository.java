package com.example.kakao.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(int num);
}
