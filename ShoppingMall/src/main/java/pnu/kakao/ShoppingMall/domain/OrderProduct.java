package pnu.kakao.ShoppingMall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_option_id")
    private ProductOption productOption;

    @Column(columnDefinition = "INT CHECK (price >= 0)")
    private int price;

    @Column(columnDefinition = "INT CHECK (count >= 0)")
    private int count;

}
