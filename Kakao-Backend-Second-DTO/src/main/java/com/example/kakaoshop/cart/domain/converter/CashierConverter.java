package com.example.kakaoshop.cart.domain.converter;

import com.example.kakaoshop.cart.domain.model.Cart;
import com.example.kakaoshop.cart.domain.model.Cashier;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CashierConverter {
    public static Cashier from(List<Cart> carts) {
        if (carts.isEmpty()) {
            return null;
        }

        return Cashier.builder()
                .carts(carts)
                .build();
    }
}
