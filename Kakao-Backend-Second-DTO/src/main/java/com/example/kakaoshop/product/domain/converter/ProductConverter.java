package com.example.kakaoshop.product.domain.converter;

import com.example.kakaoshop.product.domain.model.Product;
import com.example.kakaoshop.product.entity.ProductEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConverter {
    public static Product from(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return Product.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .productName(entity.getProductName())
                .image(entity.getImage())
                .build();
    }
}
