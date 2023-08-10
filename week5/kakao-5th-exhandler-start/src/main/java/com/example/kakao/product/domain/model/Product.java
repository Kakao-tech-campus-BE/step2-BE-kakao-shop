package com.example.kakao.product.domain.model;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Product {
    private Long productId;
    private String productName;
    private String description;
    private String image;
    private int price;
}