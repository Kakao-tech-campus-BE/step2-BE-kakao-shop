package com.example.kakaoshop.cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartResponseUpdateDTO {

    private List<CartProductDTO> orderProducts;
    private Integer totalPrice;


    // todo 의문점 : 굳이 빌더 패턴을 사용해야하는 가?? 빌더 패턴의 장점은 코드의 가독성인데 필드가 2개밖에 없는 데...
}
