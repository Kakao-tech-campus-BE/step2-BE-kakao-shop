package com.example.kakaoshop.order.item.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDTO {
    private int id;
    //DTO 출력 상으로는 optionName이 필요하지만 서비스단에서 회원이 주문한 order-ProductOption으로 접근하여 적절한 optionName을 가져와야할것 같다.
    private String optionName;
    private int quantity;
    private int price;


    @Builder
    public OrderItemDTO(int id, String optionName, int quantity, int price) {
        this.id = id;
        this.optionName = optionName;
        this.quantity = quantity;
        this.price = price;
    }
}
