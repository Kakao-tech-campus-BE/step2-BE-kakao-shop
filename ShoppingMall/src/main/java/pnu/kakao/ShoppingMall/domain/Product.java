package pnu.kakao.ShoppingMall.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    // 상품의 설명을 name으로 하다보니 Text type으로 정의
    @Column(name="product_name",columnDefinition = "TEXT")
    private String name;

    // 이미지 여러개 넣을려면 테이블이 하나 더 필요할거 같음
    private String image;

//    @Column(columnDefinition = "TEXT")
//    private String description;

    // 여기 어떤 값을..?
    @Column(columnDefinition = "INT CHECK (price >= 0)")
    private int price;

    @OneToMany(mappedBy="product")
    private List<ProductOption> productOptions = new ArrayList<>();

    private Timestamp createdAt;

    @PrePersist
    void createdAt(){
        this.createdAt = Timestamp.from(Instant.now());
    }

}
