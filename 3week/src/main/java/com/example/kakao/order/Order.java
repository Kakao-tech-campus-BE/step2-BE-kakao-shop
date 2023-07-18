package com.example.kakao.order;

import com.example.kakao.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
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
    private User user;

    @CreatedDate
    private LocalDateTime orderedAt;

    @Builder
    public Order(int id, User user) {
        this.id = id;
        this.user = user;
    }
}
