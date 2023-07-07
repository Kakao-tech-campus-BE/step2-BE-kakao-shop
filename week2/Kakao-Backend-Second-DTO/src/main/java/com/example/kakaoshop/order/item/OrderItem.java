package com.example.kakaoshop.order.item;

import com.example.kakaoshop._core.utils.BaseEntity;
import com.example.kakaoshop.order.Order;
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
@Table(name = "order_item_tb", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_order_item_order_option",
                columnNames = {"order_id", "option_id"}
        )
})
public class OrderItem extends BaseEntity {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id", nullable = false)
        private Order order;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "option_id", nullable = false)
        private ProductOption productOption;

        private int quantity;

        private int price;

        @Builder
        public OrderItem(Long id, Order order, ProductOption productOption, int quantity, int price) {
                this.id = id;
                this.order = Objects.requireNonNull(order);
                this.productOption = Objects.requireNonNull(productOption);
                this.quantity = isValidQuantity(quantity);
                this.price = isValidPrice(price);
        }

        public OrderItem changeQuantity(int quantity) {
                return OrderItem.builder()
                        .order(order)
                        .productOption(productOption)
                        .quantity(quantity)
                        .price(price)
                        .build();
        }

        public OrderItem changePrice(int price) {
                return OrderItem.builder()
                        .order(order)
                        .productOption(productOption)
                        .quantity(quantity)
                        .price(price)
                        .build();
        }

        private int isValidPrice(int price){
                if(price < 0) throw new RuntimeException("가격은 음수가 될 수 없습니다.");
                return price;
        }

        private int isValidQuantity(int quantity){
                if(quantity < 0) throw new RuntimeException("갯수는 음수가 될 수 없습니다.");
                return quantity;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                OrderItem orderItem = (OrderItem) o;
                return getQuantity() == orderItem.getQuantity() && getPrice() == orderItem.getPrice() && Objects.equals(getId(), orderItem.getId()) && Objects.equals(getOrder(), orderItem.getOrder()) && Objects.equals(getProductOption(), orderItem.getProductOption());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getId(), getOrder(), getProductOption(), getQuantity(), getPrice());
        }
}
