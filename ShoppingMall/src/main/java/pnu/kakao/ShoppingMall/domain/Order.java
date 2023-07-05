package pnu.kakao.ShoppingMall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderItems = new ArrayList<>();
    private Timestamp createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = Timestamp.from(Instant.now());
    }

    // 연관관계 메소드
//    public void setMember(Member member){
//        this.member = member;
//        member.getOrders().add(this);
//    }
//    public void addOrderItem(OrderProduct orderItem){
//        orderItems.add(orderItem);
//        orderItem.setOrder(this);
//    }

}

// TODO : 배송, OrderStatus (주문 취소) 관련

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name ="delivery_id")
//    private Delivery delivery;

//    @Enumerated(EnumType.STRING)
//    private OrderStatus status;

//    public void setDelivery(Delivery delivery){
//        this.delivery = delivery;
//        delivery.setOrder(this);
//    }