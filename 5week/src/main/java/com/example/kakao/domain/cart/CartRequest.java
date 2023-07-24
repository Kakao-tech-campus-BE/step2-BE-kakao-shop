package com.example.kakao.domain.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

public class CartRequest {

    @Getter @Setter @ToString @Builder
    public static class SaveDTO {
        @Positive(message = "옵션 아이디는 1 이상이어야 합니다.")
        private int optionId;
        @Positive(message = "수량은 1 이상이어야 합니다.")
        private int quantity;
    }

    @Getter @Setter @ToString @Builder
    public static class UpdateDTO {
        @Positive(message = "카트 아이디는 1 이상이어야 합니다.")
        private int cartId;
        @Positive(message = "수량은 1 이상이어야 합니다.")
        private int quantity;
    }
}
