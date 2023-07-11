package com.example.kakaoshop.product.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_option_tb",
        indexes = {
                @Index(name = "product_option_product_id_idx", columnList = "product_id")
        })
public class ProductOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    @Column(length = 100, nullable = false)
    private String optionName;
    private int price;

    @Builder
    public ProductOptionEntity(int id, ProductEntity product, String optionName, int price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
