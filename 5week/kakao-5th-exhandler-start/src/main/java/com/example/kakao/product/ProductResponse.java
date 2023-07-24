package com.example.kakao.product;

import lombok.Getter;
import lombok.Setter;

public class ProductResponse {
    @Getter
    @Setter
    public static class FindAllDTO {

        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        // 깊은 복사
        // 객체를 통으로 받아서 안에서 처리
        // -> view에서 messageConverter가 발생할 때 lazy loading 같은 변수가 안생김
        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }
}