package com.example.kakao.product.repository;

import com.example.kakao.product.entity.ProductEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.product.util.ProductDummyData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Repository
public class MemoryProductOptionRepository {

    private final Map<Long, ProductOptionEntity> productOptionEntities;
    private Long count = 0L;

    public MemoryProductOptionRepository(ProductDummyData dummyData) {
        productOptionEntities = new ConcurrentHashMap<>();
        dummyData.optionDummyList()
                .forEach(i -> productOptionEntities.put(count++, i));
    }

    public List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity) {
        return productOptionEntities.values()
                .stream()
                .filter(x -> x.getProduct().getId().equals(productEntity.getId()))
                .collect(Collectors.toList());
    }

    public Optional<ProductOptionEntity> findById(Long productOptionId) {
        return productOptionEntities.values()
                .stream()
                .filter(x -> x.getId().equals(productOptionId))
                .findAny();
    }
}
