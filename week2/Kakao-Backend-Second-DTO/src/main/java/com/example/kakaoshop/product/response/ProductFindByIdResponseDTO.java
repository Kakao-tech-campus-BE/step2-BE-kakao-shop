package com.example.kakaoshop.product.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProductFindByIdResponseDTO {
    @Getter
    @Builder
    public static class Response {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount; // 0~5
        private List<Option> options;

        public Response(int id, String productName, String description, String image, int price, int starCount, List<Option> options) {
            this.id = id;
            this.productName = productName;
            this.description = description;
            this.image = image;
            this.price = price;
            this.starCount = starCount;
            this.options = options;
        }
    }

    @Getter
    @Builder
    public static class Option {
        private int id;
        private String optionName;
        private int price;

        public Option(int id, String optionName, int price) {
            this.id = id;
            this.optionName = optionName;
            this.price = price;
        }
    }
}
