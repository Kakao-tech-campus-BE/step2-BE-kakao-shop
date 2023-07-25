package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
    @Getter
    public static class FindAllDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;

        @Builder
        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @Getter
    public static class FindByIdDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount; // 0~5
        private List<OptionDTO> options;

        @Builder
        public FindByIdDTO(List<Option> optionsList) {
            this.id = optionsList.get(0).getProduct().getId();
            this.productName = optionsList.get(0).getProduct().getProductName();
            this.description = optionsList.get(0).getProduct().getDescription();
            this.image = optionsList.get(0).getProduct().getImage();
            this.price = optionsList.get(0).getProduct().getPrice();
            this.starCount = 5;
            this.options = optionsList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class OptionDTO {
            int id;
            String optionName;
            int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }
}
