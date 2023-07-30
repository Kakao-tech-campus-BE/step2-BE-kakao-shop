package com.example.kakao.cart.repository;

import com.example.kakao.cart.domain.service.CartRepository;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {
    private final CartJpaRepository cartRepository;

    @Override
    public CartEntity save(CartEntity entity) {
        return cartRepository.save(entity);
    }

    @Override
    public List<CartEntity> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Optional<CartEntity> findByProductOptionEntity(ProductOptionEntity productOptionEntity) {
        return cartRepository.findByProductOptionEntity(productOptionEntity);
    }

    @Override
    public Optional<CartEntity> findByCartIdAndUser(Long cartId, User user) {
        return cartRepository.findByCartIdAndUser(cartId, user);
    }

    @Override
    public CartEntity saveAndFlush(CartEntity entity) {
        return cartRepository.saveAndFlush(entity);
    }

    @Override
    public void deleteAll(List<CartEntity> entities) {
        cartRepository.deleteAll(entities);
    }

    @Override
    public void deleteAllInBatch(List<CartEntity> entities) {
        cartRepository.deleteAllInBatch(entities);
    }
}
