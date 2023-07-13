package com.example.kakaoshop.cart.repository;

import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.cart.util.CartDummyData;
import com.example.kakaoshop.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public List<CartEntity> findByUser(User user) {
        return cartEntities.values().stream()
                .filter(x -> Objects.equals(x.getUser().getId(), user.getId()))
                .collect(Collectors.toList());
    }
}
