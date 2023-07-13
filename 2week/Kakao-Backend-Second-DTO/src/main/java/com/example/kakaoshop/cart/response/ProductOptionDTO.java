package com.example.kakaoshop.cart.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductOptionDTO {

    private int cartId;
    private int optionId;

    private String optionName;
    private int price;
    private int quantity;

    @Builder
    public ProductOptionDTO(int cartIdid, String optionName, int price, int quantity) {
        this.cartId = cartId;
        this.optionId = optionId;
        this.optionName = optionName;
        this.price = price;
        this.quantity = quantity;
    }
}
@Getter @Setter
//최종적으로 CartsUpdate 데이터 반환 DTO
public class CartsFindAllDTO {
    private List<ProductOptionDTO> carts;
    private int totalPrice;
    @Builder
    public CartsFindAllDTO(List<ProductOptionDTO> carts, int totalPrice) {
        this.carts = carts;
        this.totalPrice = totalPrice;
    }
}