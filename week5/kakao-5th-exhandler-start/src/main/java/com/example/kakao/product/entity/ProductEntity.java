package com.example.kakao.product.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_entity")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(length = 100, nullable = false)
    private String productName;
    @Column(length = 1000, nullable = false)
    private String description;
    @Column(length = 500)
    private String image;
    private int price;


    @Builder
    public ProductEntity(Long productId, String productName, String description, String image, int price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
