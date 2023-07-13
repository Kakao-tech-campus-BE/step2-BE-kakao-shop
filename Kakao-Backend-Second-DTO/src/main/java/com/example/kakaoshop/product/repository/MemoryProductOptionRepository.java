package com.example.kakaoshop.product.repository;

import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import com.example.kakaoshop.product.util.ProductDummyData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryProductOptionRepository {

    private Map<Long, ProductOptionEntity> productOptionEntities;
    private Long count = 0L;

    public MemoryProductOptionRepository(ProductDummyData dummyData) {
        productOptionEntities = new ConcurrentHashMap<>();
        dummyData.optionDummyList()
                .forEach(i -> productOptionEntities.put(count++, i));
    }

    public List<ProductOptionEntity> findByProductEntity(ProductEntity productEntity) {
        return productOptionEntities.values()
                .stream()
                .filter(x -> x.getProduct().getId() == productEntity.getId())
                .collect(Collectors.toList());
    }

    public Optional<ProductOptionEntity> findById(int productOptionId) {
        return productOptionEntities.values()
                .stream()
                .filter(x -> x.getId() == productOptionId)
                .findAny();
    }
}
