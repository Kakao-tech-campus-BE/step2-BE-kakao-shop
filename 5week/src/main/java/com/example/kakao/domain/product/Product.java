package com.example.kakao.domain.product;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_tb")
public class   Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String productName;
    @Column(length = 1000, nullable = false)
    private String description;
    @Column(length = 500)
    private String image;
    private int price; // 톡딜가
    // Product 에 price 가 있다는 것은, Option 없이 Product 만으로도 판매가 가능하다는 의미로 일단 결론지었습니다.

    @Builder
    public Product(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
