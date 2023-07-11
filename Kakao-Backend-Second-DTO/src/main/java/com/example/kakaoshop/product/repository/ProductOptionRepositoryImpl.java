package com.example.kakaoshop.product.repository;

import com.example.kakaoshop.product.domain.service.ProductOptionRepository;
import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionRepository {
    private final MemoryProductOptionRepository productOptionRepository;

    @Override
    public List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity) {
        return productOptionRepository.findByProductEntity(productEntity);
    }
}
