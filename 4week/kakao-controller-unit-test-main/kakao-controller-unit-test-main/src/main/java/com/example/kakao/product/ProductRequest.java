package com.example.kakao.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
public class ProductRequest {
    private String productName;
    private String description;
    private String image;
    private int price;
}
