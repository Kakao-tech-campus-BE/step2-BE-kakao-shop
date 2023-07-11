package com.example.kakaoshop.product.web.converter;

import com.example.kakaoshop.product.domain.converter.ProductOptionConverter;
import com.example.kakaoshop.product.domain.model.Product;
import com.example.kakaoshop.product.domain.model.ProductOption;
import com.example.kakaoshop.product.web.response.ProductReponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ProductResponseConverter {
    public static ProductReponse.ProductFindByIdResponse from(Product product, List<ProductOption> productOptions) {
        return ProductReponse.ProductFindByIdResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .starCount(0)
                .options(
                        productOptions.stream().map(ProductOptionResponseConverter::from).collect(Collectors.toList())
                )
                .build();
    }

    public static ProductReponse.ProductFindAllResponse from(Product product){
        return ProductReponse.ProductFindAllResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .build();
    }
}
