package com.example.kakaoshop.domain.product.option;

import com.example.kakaoshop.domain.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// 재고 관리 여부는 없다.

// Option 정의
// Product 가 Option 을 0 개 가지고있을 경우, 최종 결제 가격은 Product 의 가격이 된다.
// Option 이 1개 이상 존재할 경우, Product 의 가격을 무시하고 Option 의 가격으로 대체한다.
// 요구사항이 변경될 때 유연하게 변경할 수 있어야 하는데 ....
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_option_tb",
        indexes = {
                @Index(name = "product_option_product_id_idx", columnList = "product_id")
        })
public class ProductOption {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(length = 100, nullable = false)
    private String name;

    private int price;


    @Builder
    public ProductOption(int id, Product product, String name, int price) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
    }

    @Builder
    public ProductOption(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

}
