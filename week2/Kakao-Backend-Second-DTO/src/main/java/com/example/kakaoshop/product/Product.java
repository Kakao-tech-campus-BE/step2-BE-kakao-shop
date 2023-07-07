package com.example.kakaoshop.product;

import com.example.kakaoshop._core.utils.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_tb")
public class Product extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String productName;
    @Column(length = 1000, nullable = false)
    private String description;

    // 이미지는 null이어도 된다고 판단 사실 Empty("")로 해도된다고 생각한다.
    @Column(length = 500)
    private String image;

    // int를 하는거 자체가 null을 입력할 수 없어짐. 즉 not null로 들어간다.
    private int price;


    // DTO에서 null, 양수등의 검증을 할 예정이므로 엔티티에선 필요없다고 판단되지만, 추가했습니다.
    @Builder
    public Product(Long id, String productName, String description, String image, int price){
        this.id = id;
        this.productName = Objects.requireNonNull(productName);
        this.description = Objects.requireNonNull(description);
        this.image = Objects.requireNonNull(image); // image도 url인지 판단하는 코드가 있었으면 좋겠음. (DTO에 있는 것이 더 좋아보인다.)
        this.price = isValidPrice(price);
    }

    private int isValidPrice(int price){
        if(price < 0) throw new RuntimeException("가격은 음수가 될 수 없습니다.");
        return price;
    }

    // 실제라면 log같은 것을 남길 수 있도록 만드는 것이 좋을 듯 하다.
    // 요즘은 객체 불변성이 중요하다고 알고있어 setter같이 상태를 변화시키기 보다 새 객체를 내어주었습니다.
    // 더 나은 방법을 찾고싶어 디자인 패턴을 조사하는 중 이는 POJO 철학에 위배할 것 같아 해당 방법으로만 작성합니다.
    public Product changePrice(int price){
        return Product.builder()
                .id(id)
                .productName(productName)
                .image(image)
                .price(price)
                .description(description)
                .build();
    }

    private Product changeProductName(String productName) {
        return Product.builder()
                .id(id)
                .productName(productName)
                .image(image)
                .price(price)
                .description(description)
                .build();
    }

    public Product changeDescription(String description) {
        return Product.builder()
                .id(id)
                .productName(productName)
                .image(image)
                .price(price)
                .description(description)
                .build();
    }

    public Product changeImage(String image) {
        return Product.builder()
                .id(id)
                .productName(productName)
                .image(image)
                .price(price)
                .description(description)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getPrice() == product.getPrice() && Objects.equals(getId(), product.getId()) && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getImage(), product.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getDescription(), getImage(), getPrice());
    }
}
