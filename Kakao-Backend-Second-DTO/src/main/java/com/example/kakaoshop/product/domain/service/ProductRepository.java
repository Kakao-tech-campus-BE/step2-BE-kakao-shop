package com.example.kakaoshop.product.domain.service;

import com.example.kakaoshop.product.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    Optional<ProductEntity> findById(Long id);

    List<ProductEntity> findAll(int page);

}
