package com.example.kakao.option;

import com.example.kakao.product.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "option_tb")
@EntityListeners(value = OptionListener.class)
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String name;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    @Builder
    public Option(int id, String name, Product product, int price) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.price = price;
    }
}
