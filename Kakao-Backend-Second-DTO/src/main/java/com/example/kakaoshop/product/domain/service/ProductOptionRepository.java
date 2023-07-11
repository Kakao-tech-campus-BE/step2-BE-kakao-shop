package com.example.kakaoshop.product.domain.service;

import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository {
    public List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity);
}
