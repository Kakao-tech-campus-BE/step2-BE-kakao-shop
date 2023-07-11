package com.example.kakaoshop.product.domain.converter;

import com.example.kakaoshop.product.domain.model.Product;
import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.product.entity.ProductEntity;
import com.example.kakaoshop.product.entity.ProductOptionEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductOptionConverter {
    public static ProductOption from(ProductOptionEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductOption.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .optionName(entity.getOptionName())
                .product(entity.getProduct())
                .build();
    }
}
