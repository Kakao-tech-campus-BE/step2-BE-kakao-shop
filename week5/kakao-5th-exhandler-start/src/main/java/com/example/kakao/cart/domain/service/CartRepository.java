package com.example.kakao.cart.domain.service;

import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository {
    CartEntity save(CartEntity entity);

    List<CartEntity> findByUser(User user);

    Optional<CartEntity> findByProductOptionEntity(ProductOptionEntity productOptionEntity);

    Optional<CartEntity>findByUserAndProductOption(User user, Long optionId);
}
