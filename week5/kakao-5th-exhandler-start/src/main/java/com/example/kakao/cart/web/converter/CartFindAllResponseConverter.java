package com.example.kakao.cart.web.converter;

import com.example.kakao.cart.domain.model.Cart;
import com.example.kakao.cart.domain.model.Cashier;
import com.example.kakao.cart.web.response.CartFindAllResponse;
import com.example.kakao.cart.web.response.CartOptionInfoResponse;
import com.example.kakao.cart.web.response.CartSingleOptionResponse;
import com.example.kakao.cart.web.response.CartSingleProductItemResponse;
import com.example.kakao.product.domain.model.Product;
import com.example.kakao.product.domain.model.ProductOption;
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
                .productId(groupByProduct.getKey().getProductId())
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
