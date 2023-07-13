package com.example.kakaoshop.order.item;

import com.example.kakaoshop.order.Order;
import com.example.kakaoshop.product.Product;
import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.*;

import javax.persistence.*;

//CREATE TABLE `order_item_tb` (
//  `id` int(11) NOT NULL AUTO_INCREMENT,
//  `option_id` int(11) NOT NULL,
//  `quantity` int(11) NOT NULL,
//  `price` int(11) NOT NULL,
//  `order_id` int(11) NOT NULL,
//  PRIMARY KEY (`id`),
//  KEY `order_item_option_id_idx` (`option_id`),
//  CONSTRAINT `order_item_option_id` FOREIGN KEY (`option_id`) REFERENCES
//`product_option_tb` (`id`),
//  KEY `order_item_order_id_idx` (`order_id`),
//  CONSTRAINT `order_item_order_id` FOREIGN KEY (`order_id`) REFERENCES
//`order_tb` (`id`)
//);
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orderItem_tb")
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private ProductOption option;
    @ManyToOne
    private Order order;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int price;


    @Builder
    public OrderItem(int id, ProductOption option, Order order, int quantity, int price) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}