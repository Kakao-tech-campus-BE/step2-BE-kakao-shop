package com.example.kakaoshop.cart.repository;

import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.cart.util.CartDummyData;
import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import com.example.kakaoshop.product.util.ProductDummyData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryCartRepository {
    private Map<Long, CartEntity> cartEntities;

    private Long count = 0L;

    public MemoryCartRepository(CartDummyData dummyData) {
        cartEntities = new ConcurrentHashMap<>();
        dummyData.cartDummyList()
                .forEach(i -> cartEntities.put(count++, i));
    }


}
