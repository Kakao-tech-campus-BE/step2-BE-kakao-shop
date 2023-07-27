package com.example.kakao.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductResponse {
    @Getter
    @Setter
    public static class FindAllDTO {

        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @Getter @Setter
    public static class FindByIdDTO {

        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount; // 0~5
        private List<OptionDTO> options;

        @Getter @Setter
        public class OptionDTO {
            private int id;
            private String optionName;
            private int price;

        }
    }
}