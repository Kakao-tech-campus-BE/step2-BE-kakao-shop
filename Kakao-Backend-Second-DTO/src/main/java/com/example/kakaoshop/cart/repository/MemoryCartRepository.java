package com.example.kakaoshop.cart.repository;

import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.cart.util.CartDummyData;
import com.example.kakaoshop.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryCartRepository{
    private Map<Long, CartEntity> cartEntities;

    private Long count = 0L;

    public MemoryCartRepository(CartDummyData dummyData) {
        cartEntities = new ConcurrentHashMap<>();
        dummyData.cartDummyList()
                .forEach(i -> cartEntities.put(count++, i));
    }

    public CartEntity save(CartEntity entity) {
        if(entity.getId()==null) {
            return cartEntities.put(count++, entity);
        }
        return cartEntities.put(entity.getId(), entity);
    }

    public List<CartEntity> findByUser(User user) {
        return cartEntities.values().stream()
                .filter(x -> x.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<CartEntity> saveAll(List<CartEntity> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }
}
