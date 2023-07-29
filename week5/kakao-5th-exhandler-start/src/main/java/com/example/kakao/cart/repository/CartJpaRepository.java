package com.example.kakao.cart.repository;

import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {
    List<CartEntity> findByUser(User user);
    Optional<CartEntity> findByProductOptionEntity(ProductOptionEntity productOptionEntity);

    @Query("select c from CartEntity c where c.user=:user and c.productOptionEntity.productOptionId=:optionId")
    Optional<CartEntity>findByUserAndProductOptionId(@Param("user") User user, @Param("optionId") Long optionId);
}
