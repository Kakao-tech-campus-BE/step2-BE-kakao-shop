package com.example.kakaoshop.cart.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartOptionInfoResponse {

    private int id;
    private String optionName;
    private int price;

}


