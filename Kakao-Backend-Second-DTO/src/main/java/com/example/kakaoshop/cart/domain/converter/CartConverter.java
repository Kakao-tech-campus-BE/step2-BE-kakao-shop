package com.example.kakaoshop.cart.domain.converter;

import com.example.kakaoshop.cart.domain.model.Cart;
import com.example.kakaoshop.cart.entity.CartEntity;
import com.example.kakaoshop.product.domain.converter.ProductOptionConverter;
import com.example.kakaoshop.product.domain.model.ProductOption;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartConverter {

    public static Cart from(CartEntity entity) {
        if (entity == null) {
            return null;
        }

        return Cart.builder()
                .id(entity.getId())
                .user(entity.getUser())
                .productOption(ProductOptionConverter.from(entity.getProductOption()))
                .quantity(entity.getQuantity())
                .build();
    }
}