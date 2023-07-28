package com.example.kakao.cart.repository;

import com.example.kakao.cart.domain.service.CartRepository;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final MemoryCartRepository cartRepository;

    @Override
    public CartEntity save(CartEntity entity) {
        return cartRepository.save(entity);
    }

    @Override
    public List<CartEntity> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public List<CartEntity> saveAll(List<CartEntity> entities) {
        return cartRepository.saveAll(entities);
    }
}
