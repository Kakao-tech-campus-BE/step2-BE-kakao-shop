package com.example.kakaoshop.product.web.converter;

import com.example.kakaoshop.product.domain.model.Product;
import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.product.web.response.ProductReponse;
import lombok.experimental.UtilityClass;

import java.util.List;

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
