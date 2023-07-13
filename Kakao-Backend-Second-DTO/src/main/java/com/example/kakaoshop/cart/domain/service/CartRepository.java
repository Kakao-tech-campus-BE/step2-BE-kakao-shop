package com.example.kakaoshop.cart.domain.service;

import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository {
    public Optional<CartEntity> save(CartEntity entity);
    public List<CartEntity> findByUser(User user);
}
