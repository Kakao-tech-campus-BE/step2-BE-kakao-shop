package com.example.kakao.cart.domain.service;

import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository {
    public CartEntity save(CartEntity entity);

    public List<CartEntity> findByUser(User user);

    public List<CartEntity> saveAll(List<CartEntity> entities);
}
