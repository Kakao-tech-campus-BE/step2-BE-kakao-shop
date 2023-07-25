package com.example.kakao.product;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.StaleStateException;

public class ProductResponse {
    @Getter
    @Builder
    public static class FindAllDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        public FindAllDTO(int id, String productName, String description, String image, int price) {
            this.id = id;
            this.productName = productName;
            this.description = description;
            this.image = image;
            this.price = price;
        }
    }


}
