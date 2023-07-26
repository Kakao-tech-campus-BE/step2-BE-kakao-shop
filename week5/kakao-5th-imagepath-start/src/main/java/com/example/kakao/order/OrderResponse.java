package com.example.kakao.order;

import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderResponse {
    @Getter
    @Setter
    public static class FindByIdDTO {
        private List<CartResponse.FindAllDTO.ProductDTO> productDTOS;
        private int id;
        private int totalPrice;

        public FindByIdDTO(Order order, List<Item> items){
        }

    }


}
