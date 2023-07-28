package com.example.kakao.product.repository;

import com.example.kakao.product.domain.service.ProductRepository;
import com.example.kakao.product.entity.ProductEntity;
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
