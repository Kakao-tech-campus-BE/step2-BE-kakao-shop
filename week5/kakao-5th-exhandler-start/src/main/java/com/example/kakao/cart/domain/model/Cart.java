package com.example.kakao.cart.domain.model;

import com.example.kakao.product.domain.model.ProductOption;
import com.example.kakao.user.User;
import lombok.*;
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Cart {
    private Long id;
    private User user;
    private ProductOption productOption;
    private int quantity;

    public boolean isSameOption(Long optionId){
        return productOption.isSameOption(optionId);
    }
}
