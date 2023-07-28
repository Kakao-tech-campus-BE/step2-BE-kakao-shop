package com.example.kakao.cart.util;

import com.example.kakao.cart.entity.CartEntity;
import com.example.kakao.product.entity.ProductOptionEntity;
import com.example.kakao.product.util.ProductDummyData;
import com.example.kakao.user.util.UserDummyData;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class CartDummyData {
    private final ProductDummyData productDummyData;
    private final UserDummyData userDummyData;

    public CartDummyData(ProductDummyData productDummyData, UserDummyData userDummyData) {
        this.productDummyData = productDummyData;
        this.userDummyData = userDummyData;
    }


    public List<CartEntity> cartDummyList() {
        return Arrays.asList(
                newCart(getProProductOptionEntities().get(0), 1L, 5),
                newCart(getProProductOptionEntities().get(1), 2L, 5)
        );
    }

    private CartEntity newCart(ProductOptionEntity option, Long id, Integer quantity) {
        return CartEntity.builder()
                .id(id)
                .user(userDummyData.newUser(1L, "ssar"))
                .productOptionEntity(option)
                .quantity(quantity)
                .build();
    }

    private List<ProductOptionEntity> getProProductOptionEntities() {
        return productDummyData.optionDummyList();
    }
}
