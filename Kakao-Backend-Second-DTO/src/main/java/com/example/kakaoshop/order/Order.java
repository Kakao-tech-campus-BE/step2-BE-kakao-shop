package com.example.kakaoshop.order;

import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany
    private List<OrderItem> orderItemList = new ArrayList<>();
}
