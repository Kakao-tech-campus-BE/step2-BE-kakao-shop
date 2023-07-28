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
@Table(name = "product_option_entity",
        indexes = {
                @Index(name = "product_option_product_id_idx", columnList = "product_id")
        }
)
public class ProductOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(length = 100, nullable = false)
    private String optionName;
    private int price;

    @Builder
    public ProductOptionEntity(Long productOptionId, ProductEntity product, String optionName, int price) {
        this.productOptionId = productOptionId;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
