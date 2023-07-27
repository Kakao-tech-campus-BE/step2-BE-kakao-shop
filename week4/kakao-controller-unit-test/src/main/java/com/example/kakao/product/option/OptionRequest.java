package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import lombok.Builder;
import lombok.Getter;

public class OptionRequest {
    @Getter
    public static class Insert {
        private final Product product;
        private final String name;
        private final int price;

        @Builder
        public Insert(Product product, String name, int price) {
            this.product = product;
            this.name = name;
            this.price = price;
        }
    }
}
