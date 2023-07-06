package com.example.kakaoshop.order;

import com.example.kakaoshop.order.item.OrderItem;
import com.example.kakaoshop.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_tb")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // TODO: auto-increment PK vs UUID 고민, 주문번호를 사람이 이해하기 쉬운 형태로 만들 필요가 있을수도 있겠다.
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  // 총 주문금액을 따로 저장할지는 얼마나 주문내역 조회가 많이 발생할지에 대해 생각해봐야 할 것 같음. -> 별로 많이 발생하지 않을듯.

  private LocalDateTime orderedAt; // createdAt, updatedAt 으로 대체할 수 있겠다.

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  @Builder
  public Order(User user, LocalDateTime orderedAt, List<OrderItem> orderItems) {
    this.user = user;
    this.orderedAt = LocalDateTime.now();
    this.orderItems = orderItems;
  }

}
