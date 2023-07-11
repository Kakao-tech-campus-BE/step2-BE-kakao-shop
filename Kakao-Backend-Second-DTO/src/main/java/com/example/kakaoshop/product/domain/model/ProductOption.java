package com.example.kakaoshop.product.domain.model;

import com.example.kakaoshop.product.entity.ProductEntity;
import lombok.*;


@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductOption {
    private int id;
    private ProductEntity product;
    private String optionName;
    private int price;
}