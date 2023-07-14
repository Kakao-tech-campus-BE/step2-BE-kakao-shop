package com.example.kakao.order;

import com.example.kakao.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="order_tb", indexes = {
        @Index(name = "order_user_id_idx", columnList = "user_id")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 여러 order를 1개 user가 가진다
    @ManyToOne
    private User user;

    @Builder
    public Order(int id, User user) {
        this.id = id;
        this.user = user;
    }
}
