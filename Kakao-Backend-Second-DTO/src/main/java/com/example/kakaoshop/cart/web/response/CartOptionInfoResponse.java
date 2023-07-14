package com.example.kakaoshop.cart.web.response;

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


