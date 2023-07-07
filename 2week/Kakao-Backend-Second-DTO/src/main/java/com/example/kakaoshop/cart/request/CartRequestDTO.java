package com.example.kakaoshop.cart.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class CartRequestDTO {
	
    @Getter
    @Setter
    @NoArgsConstructor
    public static class AddDTO {
        @NotNull
        private Long optionId;

        @Min(1)
        private int quantity;

        @Builder
        public AddDTO(Long optionId, int quantity) {
            this.optionId = optionId;
            this.quantity = quantity;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateDTO {
        @NotNull
        private Long cartId;

        @Min(1)
        private int quantity;

        @Builder
        public UpdateDTO(Long cartId, int quantity) {
            this.cartId = cartId;
            this.quantity = quantity;
        }
    }

}
