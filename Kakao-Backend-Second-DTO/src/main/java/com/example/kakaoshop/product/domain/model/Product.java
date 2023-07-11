package com.example.kakaoshop.product.domain.model;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {
    private int id;
    private String productName;
    private String description;
    private String image;
    private int price;
}