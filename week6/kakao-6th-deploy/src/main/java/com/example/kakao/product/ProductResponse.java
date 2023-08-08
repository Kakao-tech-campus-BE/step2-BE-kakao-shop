package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    /* 전체상품조회를 위한 DTO */
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

    /* Product, Option을 따로 쿼리하는 방식의 DTO */
    @Getter
    @Setter
    public static class FindByIdDTO {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.options = optionList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public static class OptionDTO {
            private int id;
            private String optionName;
            private int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }

    /* 한방쿼리 DTO */
    @Getter
    @Setter
    public static class FindByIdDTOv2 {
        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private List<OptionDTO> options;

        public FindByIdDTOv2(List<Option> optionList) {
            this.id = optionList.get(0).getProduct().getId();
            this.productName = optionList.get(0).getProduct().getProductName();
            this.description = optionList.get(0).getProduct().getDescription();
            this.image = optionList.get(0).getProduct().getImage();
            this.price = optionList.get(0).getProduct().getPrice();
            this.options = optionList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public static class OptionDTO {
            private int id;
            private String optionName;
            private int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }
}
