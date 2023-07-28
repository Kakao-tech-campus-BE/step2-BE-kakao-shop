package com.example.kakao.product.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProductReponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class ProductFindAllResponse {

        private Long id;
        private String productName;
        private String description;
        private String image;
        private int price;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class ProductOptionResponse {

        private Long id;
        private String optionName;
        private int price;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder(toBuilder = true)
    public static class ProductFindByIdResponse {

        private Long id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount;
        private List<ProductOptionResponse> options;
    }
}
