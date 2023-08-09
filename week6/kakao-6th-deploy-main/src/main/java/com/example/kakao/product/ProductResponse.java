package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    @Getter
    public static class FindAllDTO {
        private final int id;
        private final String productName;
        private final String description;
        private final String image;
        private final int price;

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
        private final int id;
        private final String productName;
        private final String description;
        private final String image;
        private final int price;
        private final int starCount;
        private final List<OptionDTO> options;

        public FindByIdDTO(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.starCount = 5; // 임시로 추가해둠 (요구사항에는 없음)
            this.options = optionList.stream().map(OptionDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class OptionDTO {
            private final int id;
            private final String optionName;
            private final int price;

            public OptionDTO(Option option) {
                this.id = option.getId();
                this.optionName = option.getOptionName();
                this.price = option.getPrice();
            }
        }
    }
}