package com.example.kakao.product.option;

import com.example.kakao.cart.CartResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class OptionResponse {
    @Getter @Setter
    public static class FindByProductIdDTO{
        private List<OptionDTO> options;

        public FindByProductIdDTO(List<Option> optionList){
            this.options = optionList.stream().map(option->new OptionDTO(option)).collect(Collectors.toList());
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
