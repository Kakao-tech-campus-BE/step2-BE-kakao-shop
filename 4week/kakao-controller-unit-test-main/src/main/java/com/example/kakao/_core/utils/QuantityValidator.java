package com.example.kakao._core.utils;

import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class QuantityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz) || Cart.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        int quantity;

        if (target instanceof Item) {
            Item item = (Item) target;
            quantity = item.getQuantity();
        } else if (target instanceof Cart) {
            Cart cart = (Cart) target;
            quantity = cart.getQuantity();
        } else {
            return;
        }

        if (quantity <= 0) {
            errors.rejectValue("quantity", "invalid", "Quantity must be greater than 0");
        }
    }
}
