package com.example.kakaoshop.order.item;

import com.example.kakaoshop.product.option.ProductOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    // 이 친구는 나중에 order item table이 됩니다
    private int id;
    private ProductOption productOption;
    private int quantity;
    private int price;
}
