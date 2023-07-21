package com.example.kakao.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ProductRequest {

    @NotEmpty(message = "productName 이 필요합니다.")
    private String productName;

    @NotEmpty(message = "description 이 필요합니다.")
    private String description;

    @NotEmpty(message = "Image 가 필요합니다.")
    private String image;

    @NotNull(message = "Price 가 필요합니다.")
    @Positive(message = "Price 는 반드시 양수이어야 합니다.")
    private int price;

}
