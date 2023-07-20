package com.example.kakao.order;

import com.example.kakao.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    // 추가. user 필드에서 user_id 컬럼을 외래 키로 사용하도록 매핑한다.
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    // 추가. 주문 총 가격
    @Column(nullable = false)
    private int totalPrice;

    // 여기도 맞춰서 코드 수정해주기
    // User에서 @NotNull을 추가한다.
    @Builder
    public Order(int id, @NotNull User user, int totalPrice) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
    }
}
