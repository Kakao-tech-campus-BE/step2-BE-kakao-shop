package com.example.kakao.product.repository;

import com.example.kakao.product.entity.ProductEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionEntity,Long> {
    List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity);
}
