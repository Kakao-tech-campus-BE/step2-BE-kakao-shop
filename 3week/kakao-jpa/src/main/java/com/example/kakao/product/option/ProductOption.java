package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
// 테이블명 변경
@Table(name="product_option_tb",
        indexes = {
                @Index(name = "option_product_id_idx", columnList = "product_id")
        })

// 여기 class명 수정 (Option -> ProductOption)
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Product product;

    @Column(length = 100, nullable = false)
    private String optionName;

    // 음수 가격은 허용하지 않음
    @Positive
    private int price;

    @Builder
    public ProductOption(int id, Product product, String optionName, int price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
