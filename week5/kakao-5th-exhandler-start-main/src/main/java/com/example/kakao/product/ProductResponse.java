package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
    @Getter
    @Builder
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

    @Getter
    @Builder
    public static class FindByIdDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount; // 0~5
        private List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> optionsList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.starCount = 5;
            this.options = optionsList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        @Builder
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

    @Getter
    @Builder
    public static class FindByIdDTOv2 {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount; // 0~5
        private List<OptionDTO> options;

        public FindByIdDTOv2(List<Option> optionsList) {
            this.id = optionsList.get(0).getProduct().getId();
            this.productName = optionsList.get(0).getProduct().getProductName();
            this.description = optionsList.get(0).getProduct().getDescription();
            this.image = optionsList.get(0).getProduct().getImage();
            this.price = optionsList.get(0).getProduct().getPrice();
            this.starCount = 5;
            this.options = optionsList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        @Builder
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
