package com.example.kakao.cart.domain.converter;

import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.product.domain.converter.ProductOptionConverter;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.user.User;
import lombok.experimental.UtilityClass;
@UtilityClass
public class CartConverter {

    public static Cart from(CartEntity entity) {
        if (entity == null) {
            return null;
        }

        return Cart.builder()
                .id(entity.getCartId())
                .user(entity.getUser())
                .productOption(ProductOptionConverter.from(entity.getProductOptionEntity()))
                .quantity(entity.getQuantity())
                .build();
    }

    public static CartEntity to(int quantity, ProductOptionEntity productOptionEntity, User user) {
        return CartEntity.builder()
                .user(user)
                .productOptionEntity(productOptionEntity)
                .quantity(quantity)
                .build();
    }
}