package com.example.kakao.cart.repository;

import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByUser(User user);

    Optional<CartEntity> findByOption(ProductOptionEntity option);

    Optional<CartEntity> findByIdAndUser(Long id, User user);
}
