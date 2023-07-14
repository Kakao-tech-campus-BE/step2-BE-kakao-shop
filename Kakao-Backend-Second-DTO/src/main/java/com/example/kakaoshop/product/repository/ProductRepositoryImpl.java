package com.example.kakaoshop.product.repository;

import com.example.kakaoshop.product.domain.service.ProductRepository;
import com.example.kakaoshop.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final MemoryProductRepository productRepository;

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductEntity> findAll(Long page) {
        return productRepository.findAll(page);
    }

}
