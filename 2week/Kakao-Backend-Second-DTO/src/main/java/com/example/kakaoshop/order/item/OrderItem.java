package com.example.kakaoshop.order.item;

import com.example.kakaoshop.cart.response.CartItemDTO;
import com.example.kakaoshop.cart.response.ProductOptionDTO;

import lombok.*;

import java.util.List;


@Getter @Setter
public class OrderItem {

        private int id;
        private String optionName;
        private int quantity;
        private int price;

        @Builder
        public OrderItem(int id, String optionName, int quantity, int price) {
                this.id = id;
                this.optionName = optionName;
                this.quantity = quantity;
                this.price = price;
        }
}
