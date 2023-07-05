package com.example.kakao.product;

import com.example.kakao.option.Option;
import com.example.kakao.option.OptionListener;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_tb")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 1000, nullable = false)
    private String image;
    private int price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<Option> options = new ArrayList<>();

    @Builder
    public Product(int id, String name, String image, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }
}
