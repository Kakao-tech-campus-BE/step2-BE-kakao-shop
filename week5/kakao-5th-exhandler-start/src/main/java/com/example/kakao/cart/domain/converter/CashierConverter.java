package com.example.kakao.cart.domain.converter;

import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.domain.model.Cashier;
import lombok.experimental.UtilityClass;

import java.util.List;
@UtilityClass
public class CashierConverter {
    public static Cashier from(List<Cart> carts) {
        if (carts.isEmpty()) {
            return null;
        }

        return Cashier.builder()
                .existingCarts(carts)
                .build();
    }
}
