package com.example.kakaoshop.domain.product;

import com.example.kakaoshop.domain.product.option.ProductOption;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_tb")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String productName;
    @Column(length = 1000, nullable = false)
    private String description;
    @Column(length = 500)
    private String image;
    private int price;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptions;


    @Builder
    public Product(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    @Builder
    public Product(int id, String productName, String description, String image, int price, List<ProductOption> productOptions) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.productOptions = productOptions;
    }
}
