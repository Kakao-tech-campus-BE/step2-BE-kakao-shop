package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class CartRespUpdateDTO {
        private List<UpdatedItemDTO> carts;
        private int totalPrice;

        @Builder
        public CartRespUpdateDTO(List<UpdatedItemDTO> carts, int totalPrice) {
            this.carts = carts;
            this.totalPrice = totalPrice;
        }
    }

