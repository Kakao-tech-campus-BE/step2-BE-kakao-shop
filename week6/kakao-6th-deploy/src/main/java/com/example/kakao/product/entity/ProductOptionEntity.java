package com.example.kakao.product.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "option_tb",
        indexes = {
                @Index(name = "option_product_id_idx", columnList = "product_id")
        }
)
public class ProductOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    @Column(length = 100, nullable = false)
    private String optionName;
    private int price;

    @Builder
    public ProductOptionEntity(Long id, ProductEntity product, String optionName, int price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
