package com.example.kakao.cart.web.converter;

import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.web.response.CartChangedOptionResponse;
import com.example.kakao.cart.web.response.CartUpdateResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CartUpdateResponseConverter {
    public CartUpdateResponse from(List<Cart> cartList, int totalPrice){
        return CartUpdateResponse.builder()
                .cartChangedOptionResponses(
                        cartList.stream()
                                .map(CartUpdateResponseConverter::toCartChangedOptionResponse)
                                .collect(Collectors.toList())
                )
                .totalPrice(totalPrice)
                .build();
    }

    private CartChangedOptionResponse toCartChangedOptionResponse(Cart cart){
        return CartChangedOptionResponse.builder()
                .cartId(cart.getId())
                .optionId(cart.getProductOption().getId())
                .optionName(cart.getProductOption().getOptionName())
                .quantity(cart.getQuantity())
                .price(cart.getProductOption().getPrice())
                .build();
    }
}
