package com.example.kakaoshop.cart;

import com.example.kakaoshop._core.utils.BaseEntity;
import com.example.kakaoshop.product.option.ProductOption;
import com.example.kakaoshop.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cart_tb", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_cart_option_user",
                columnNames = {"option_id", "user_id"}
        )}
)
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption productOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int quantity;

    @Builder
    public Cart(Long id, ProductOption productOption, User user, int quantity) {
        this.id = id;
        this.productOption = Objects.requireNonNull(productOption);
        this.user = Objects.requireNonNull(user);
        this.quantity = isValidQuantity(quantity);
    }

    public Cart changeProductOption(ProductOption productOption) {
        return Cart.builder()
                .id(id)
                .productOption(productOption)
                .user(user)
                .quantity(quantity)
                .build();
    }

    public Cart changeUser(User user) {
        return Cart.builder()
                .id(id)
                .productOption(productOption)
                .user(user)
                .quantity(quantity)
                .build();
    }

    public Cart changeQuantity(int quantity) {
        return Cart.builder()
                .id(id)
                .productOption(productOption)
                .user(user)
                .quantity(quantity)
                .build();
    }

    private int isValidQuantity(int quantity){
        if(quantity < 0) throw new RuntimeException("갯수는 음수가 될 수 없습니다.");
        return quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return getQuantity() == cart.getQuantity() && Objects.equals(getId(), cart.getId()) && Objects.equals(getProductOption(), cart.getProductOption()) && Objects.equals(getUser(), cart.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductOption(), getUser(), getQuantity());
    }
}
