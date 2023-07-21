package com.example.kakaoshop.product.domain.model;

import lombok.*;


@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductOption {
    private Long id;
    private Product product;
    private String optionName;
    private int price;
}