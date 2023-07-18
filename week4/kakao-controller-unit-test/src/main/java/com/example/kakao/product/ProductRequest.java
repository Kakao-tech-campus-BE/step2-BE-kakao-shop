package com.example.kakao.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ProductRequest {
    @Getter
    public static class Insert {
        private final String name;
        private final String description;
        private final String image;
        private final int price;

        @Builder
        public Insert(String name, String description, String image, int price) {
            this.name = name;
            this.description = description;
            this.image = image;
            this.price = price;
        }
    }
}
