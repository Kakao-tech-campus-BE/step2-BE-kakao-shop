package com.example.kakao.product.repository;

import com.example.kakao.product.entity.ProductEntity;
import com.example.kakao.product.util.ProductDummyData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
@Repository
public class MemoryProductRepository {
    private final Map<Long, ProductEntity> productEntities;
    private Long count = 0L;

    public MemoryProductRepository(ProductDummyData dummyData) {
        productEntities = new ConcurrentHashMap<>();
        dummyData.productDummyList()
                .forEach(i -> productEntities.put(count++, i));
    }

    Optional<ProductEntity> findById(Long id) {
        if (!productEntities.containsKey(id)) {
            // TODO : 추후 ExceptionHandler 사용
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        }
        return Optional.ofNullable(productEntities.get(id));
    }

    List<ProductEntity> findAll(int page) {
        if (!productEntities.containsKey(page * 9L)) {
            // TODO : 추후 ExceptionHandler 사용
            throw new IllegalArgumentException("해당 페이지에 상품이 존재하지 않습니다.");
        }
        return productEntities.values().stream()
                .skip(page * 9L).limit(9).collect(Collectors.toList());
    }
}
