package com.example.kakaoshop.cart.domain.converter;

import com.example.kakaoshop.cart.domain.model.Cart;
import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.product.domain.converter.ProductOptionConverter;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import com.example.kakaoshop.user.User;
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
                .productOption(ProductOptionConverter.from(entity.getProductOption()))
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