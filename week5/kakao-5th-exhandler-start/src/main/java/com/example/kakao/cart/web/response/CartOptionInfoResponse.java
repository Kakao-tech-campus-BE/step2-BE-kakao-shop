package com.example.kakao.cart.web.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartOptionInfoResponse {

    private Long id;
    private String optionName;
    private int price;

}


