package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartUpdateDTO {
    private Long cartId;
    private Long optionId;
    private String optionName;
    private int quantity;
    private int price;

    @Builder
    public CartUpdateDTO(Long cartId, Long optionId, String optionName, int quantity, int price)
    {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }

    // userRequest 참고
    @Getter
    @Setter
    public static class UpdateDTO {
        private Long cartId;
        private int quantity;

        @Builder
        public UpdateDTO(Long cartId, int quantity)
        {
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }
}
