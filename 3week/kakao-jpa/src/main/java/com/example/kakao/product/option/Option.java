package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import lombok.*;

import javax.persistence.*;

// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name="option_tb",
        indexes = {
                @Index(name = "option_product_id_idx", columnList = "product_id")
        })
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // todo Product에 cascadeType.Persist 속성을 넣는 것 vs Product와 option의 연관관계를 끊는 것 어떤 것이 더 좋을까?
    // 연관관계를 끊는 이유 : 단방향 연관관계를 사용하는 이유는 뭘까??
    @ManyToOne
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
