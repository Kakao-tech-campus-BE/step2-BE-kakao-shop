package com.example.kakaoshop.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OptionDTO {
    private int id;
    private String optionName;
    private int quantity;
    private int price;
}
