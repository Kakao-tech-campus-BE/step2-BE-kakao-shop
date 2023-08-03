package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    @Getter @Setter
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
        private int starCount;
        private List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> options) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.starCount = 5;
            this.options = options.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter @Setter
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

    // 조인해서 가져오기
    //
    @Getter @Setter
    public static class FindByIdDTOv2 {

        private int id;
        private String productName;
        private String description;
        private String image;
        private int price;
        private int starCount;
        private List<OptionDTO> options;

        public FindByIdDTOv2(List<Option> options) {
            this.id = options.get(0).getProduct().getId();
            this.productName = options.get(0).getProduct().getProductName();
            this.description = options.get(0).getProduct().getDescription();
            this.image = options.get(0).getProduct().getImage();
            this.price = options.get(0).getProduct().getPrice();
            this.starCount = 5;
            this.options = options.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter @Setter
        public class OptionDTO {
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
