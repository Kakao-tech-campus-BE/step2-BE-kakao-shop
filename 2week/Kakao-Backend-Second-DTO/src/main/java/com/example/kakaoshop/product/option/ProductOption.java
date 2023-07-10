package com.example.kakaoshop.product.option;

import com.example.kakaoshop.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// 재고 관리 여부는 없다.
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
    private Product product;


    @Column(length = 100, nullable = false)
    private String optionName;
    private int price;


    @Builder
    public ProductOption(int id, Product product, String optionName, int price) {
        this.id = id;
        this.product = product;
        this.optionName = optionName;
        this.price = price;
    }
}
