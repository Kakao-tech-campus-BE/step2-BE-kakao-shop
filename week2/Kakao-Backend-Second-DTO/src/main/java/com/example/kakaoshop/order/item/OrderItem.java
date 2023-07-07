package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.Order;
import com.example.kakaoshop.product.option.ProductOption;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne
    @JoinColumn(name = "PRODUCT_OPTION_ID")
    private ProductOption productOption;

    private int quantity;

}
