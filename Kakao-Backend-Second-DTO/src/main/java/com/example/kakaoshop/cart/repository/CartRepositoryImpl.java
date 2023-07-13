package com.example.kakaoshop.cart.repository;

import com.example.kakaoshop.cart.domain.service.CartRepository;
import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final MemoryCartRepository cartRepository;

    @Override
    public Optional<CartEntity> save(CartEntity entity) {
        return Optional.empty();
    }

    @Override
    public List<CartEntity> findByUser(User user) {
        return cartRepository.findByUser(user);
    }
}
