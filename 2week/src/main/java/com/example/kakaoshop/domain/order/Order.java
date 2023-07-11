package com.example.kakaoshop.domain.order;

import com.example.kakaoshop.domain.order.item.OrderItem;
import com.example.kakaoshop.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @JoinColumn(name = "account_id")
  private Account account;

  // 총 주문금액을 따로 저장할지는 얼마나 주문내역 조회가 많이 발생할지에 대해 생각해봐야 할 것 같음. -> 별로 많이 발생하지 않을듯.

  private LocalDateTime orderedAt; // createdAt, updatedAt 으로 대체할 수 있겠다.

  @Setter
  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  @Builder
  public Order(Account account, List<OrderItem> orderItems) {
    this.account = account;
    this.orderedAt = LocalDateTime.now();
    this.orderItems = orderItems;
  }

  @Builder
  public Order(Account account) {
    this.account = account;
    this.orderedAt = LocalDateTime.now();
  }


}
