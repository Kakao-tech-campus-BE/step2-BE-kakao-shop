package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import lombok.*;

import javax.persistence.*;

// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="option_tb",
        indexes = {
                @Index(name = "option_product_id_idx", columnList = "product_id")
        })
public class Option { // option이 연관관계 주인
    // option 조회할 때 product를 조인해서 조회하면 되지 않을까?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 기본전략을 lazy로 하기로 했으니까 바꾸자
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(length = 100, nullable = false)
    private String optionName;
    private int price;

    @Builder
    public Option(int id, Product product, String optionName, int price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
