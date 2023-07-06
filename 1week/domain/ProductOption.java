package pnu.kakao.ShoppingMall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="product_option_name", length=100)
    private String name;

    @Column(columnDefinition = "INT CHECK (price >= 0)")
    private int price;

    @Column(columnDefinition = "INT CHECK (stock_quantity >= 0)")
    private int stockQuantity;

    private Timestamp createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = Timestamp.from(Instant.now());
    }
}
