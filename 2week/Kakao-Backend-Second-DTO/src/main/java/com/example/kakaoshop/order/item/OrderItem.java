package com.example.kakaoshop.order.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {
    // 관계설정안함 - 금주 과제 아님
    // DB 테이블 수동연결안함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 11, nullable = false)
    private int optionid;
    @Column(length = 11, nullable = false)
    private int quantity;
    @Column(length = 11, nullable = false)
    private int price;
}
