package com.example.kakao.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    // 수정: 현재 이 price 는 비즈니스 상 아무런 의미를 갖지 않는 값이다. Product 는 반드시 1개 이상의 Option 을 가져야하고 그렇지않으면 오류로 간주한다.

    @Builder
    public Product(int id, String productName, String description, String image, int price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
