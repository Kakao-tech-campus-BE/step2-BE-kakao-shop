package com.example.kakao.product.domain.service;

import com.example.kakao.product.entity.ProductEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductOptionRepository {
    public List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity);

    public Optional<ProductOptionEntity> findById(Long productOptionId);
}
