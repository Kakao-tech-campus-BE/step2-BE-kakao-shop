package com.example.kakao.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {
    
}
