package com.example.kakao.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CartRequest {
    //약속된 부분이므로 지금은 변경 x. price까지 깔끔하게 받아오고 검증하는게 나을수도 있음
    @Getter @Setter @ToString
    public static class SaveDTO {
        private int optionId;
        private int quantity;
    }

    @Getter @Setter @ToString
    public static class UpdateDTO {
        private int cartId;
        private int quantity;
    }
}
