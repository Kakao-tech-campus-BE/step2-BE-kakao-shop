package com.example.kakaoshop.cart.web.converter;

import com.example.kakaoshop.cart.domain.model.Cart;
import com.example.kakaoshop.cart.domain.model.Cashier;
import com.example.kakaoshop.cart.web.response.CartFindAllResponse;
import com.example.kakaoshop.cart.web.response.CartOptionInfoResponse;
import com.example.kakaoshop.cart.web.response.CartSingleOptionResponse;
import com.example.kakaoshop.cart.web.response.CartSingleProductItemResponse;
import com.example.kakaoshop.product.domain.model.Product;
import com.example.kakaoshop.product.domain.model.ProductOption;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class CartFindAllResponseConverter {

    public static CartFindAllResponse from(Cashier cashier, int totalPrice) {
        return CartFindAllResponse.builder()
                .products(
                        cashier.groupByProduct().stream()
                                .map(CartFindAllResponseConverter::toCartSingleProductItemResponse)
                                .collect(Collectors.toList())
                )
                .totalPrice(totalPrice)
                .build();
    }

    private CartSingleProductItemResponse toCartSingleProductItemResponse(Map.Entry<Product, List<Cart>> groupByProduct) {
        return CartSingleProductItemResponse.builder()
                .productId(groupByProduct.getKey().getId())
                .productName(groupByProduct.getKey().getProductName())
                .cartItems(
                        groupByProduct.getValue().stream()
                                .map(CartFindAllResponseConverter::toCartSingleOptionResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private CartSingleOptionResponse toCartSingleOptionResponse(Cart cart) {
        return CartSingleOptionResponse.builder()
                .cartId(cart.getId())
                .option(toCartOptionInfoResponse(cart.getProductOption()))
                .quantity(cart.getQuantity())
                .price(cart.getProductOption().getPrice())
                .build();
    }

    private CartOptionInfoResponse toCartOptionInfoResponse(ProductOption productOption) {
        return CartOptionInfoResponse.builder()
                .id(productOption.getId())
                .optionName(productOption.getOptionName())
                .price(productOption.getPrice())
                .build();
    }

}
