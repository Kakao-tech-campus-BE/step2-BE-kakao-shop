package com.example.kakao.product.repository;

import com.example.kakao.product.domain.service.ProductOptionRepository;
import com.example.kakao.product.entity.ProductEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionRepository {
    private final ProductOptionJpaRepository productOptionRepository;

    @Override
    public Optional<ProductOptionEntity> findById(Long productOptionId) {
        return productOptionRepository.findById(productOptionId);
    }

    @Override
    public List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity) {
        return productOptionRepository.findByProductEntity(productEntity);
    }
}
