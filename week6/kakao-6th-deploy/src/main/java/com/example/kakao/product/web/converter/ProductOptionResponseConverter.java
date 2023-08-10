package com.example.kakao.product.web.converter;

import com.example.kakao.product.domain.model.ProductOption;
import com.example.kakao.product.web.response.ProductReponse;
import lombok.experimental.UtilityClass;
@UtilityClass
public class ProductOptionResponseConverter {
    public static ProductReponse.ProductOptionResponse from(ProductOption productOption) {
        return ProductReponse.ProductOptionResponse.builder()
                .id(productOption.getId())
                .optionName(productOption.getOptionName())
                .price(productOption.getPrice())
                .build();
    }
}
