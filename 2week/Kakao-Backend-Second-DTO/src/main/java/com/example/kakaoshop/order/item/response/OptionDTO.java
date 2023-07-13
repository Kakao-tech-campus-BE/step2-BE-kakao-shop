package com.example.kakaoshop.order.item.response;

import lombok.Builder;
import lombok.Data;

@Data
public class OptionDTO {

    private Long id;
    private String optionName;
    private Integer quantity;
    private Integer price;

    @Builder
    public OptionDTO(Long id, String optionName, Integer quantity, Integer price) {
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }

    // todo dto의 필드에 final을 붙여야 하는 지에 대해서 생각해보기
    // todo dto의 역할이 뭔지에 대해 생각해보기
}
