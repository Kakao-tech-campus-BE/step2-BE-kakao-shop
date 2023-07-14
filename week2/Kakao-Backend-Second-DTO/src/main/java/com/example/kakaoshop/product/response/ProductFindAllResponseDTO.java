package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;

public class ProductFindAllResponseDTO {
    @Getter
    @Builder
    public static class Response {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        public Response(int id, String productName, String description, String image, int price) {
            this.id = id;
            this.productName = productName;
            this.description = description;
            this.image = image;
            this.price = price;
        }
    }
}
