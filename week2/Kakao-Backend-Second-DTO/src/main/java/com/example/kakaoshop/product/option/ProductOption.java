package com.example.kakaoshop.product.option;

import com.example.kakaoshop._core.utils.BaseEntity;
import com.example.kakaoshop.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


// 재고 관리 여부는 없다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="product_option_tb",
        indexes = {
                @Index(name = "product_option_product_id_idx", columnList = "product_id")
        })
public class ProductOption extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(length = 100, nullable = false)
    private String optionName;

    // int는 자동으로 not null이 생성
    private int price;


    @Builder
    public ProductOption(Long id, Product product, String optionName, int price){
        this.id = id;
        this.product = Objects.requireNonNull(product);
        this.optionName = Objects.requireNonNull(optionName);
        this.price = isValidPrice(price);
    }

    private int isValidPrice(int price){
        if(price < 0) throw new RuntimeException("가격은 음수가 될 수 없습니다.");
        return price;
    }

    public ProductOption changeProduct(Product product) {
        return ProductOption.builder()
                .id(id)
                .product(product)
                .optionName(optionName)
                .price(price)
                .build();
    }

    public ProductOption changeOptionName(String optionName) {
        return ProductOption.builder()
                .id(id)
                .product(product)
                .optionName(optionName)
                .price(price)
                .build();
    }

    public ProductOption changePrice(int price) {
        return ProductOption.builder()
                .id(id)
                .product(product)
                .optionName(optionName)
                .price(price)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOption that = (ProductOption) o;
        return getPrice() == that.getPrice() && Objects.equals(getId(), that.getId()) && Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getOptionName(), that.getOptionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getOptionName(), getPrice());
    }
}
