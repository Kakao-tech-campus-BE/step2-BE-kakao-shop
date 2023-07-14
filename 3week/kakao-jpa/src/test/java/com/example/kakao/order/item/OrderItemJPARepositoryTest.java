package com.example.kakao.order.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderItemJPARepository orderItemRepository;

    @Autowired
    private OrderJPARepository orderRepository;

    @Autowired
    private UserJPARepository userRepository;

    @BeforeEach
    public void setUp() {
        User user1 = userRepository.save(newUser("ssar"));
        entityManager.persist(user1);

        Order order1 = orderRepository.save(newOrder(user1));
        entityManager.persist(order1);

        Product product1 = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        entityManager.persist(product1);

        ProductOption productOption1 = newProductOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        entityManager.persist(productOption1);

        Cart cart1 = newCart(user1, productOption1, 5);
        entityManager.persist(cart1);

        OrderItem item1 = newOrderItem(cart1, order1);

        entityManager.persist(item1);

        entityManager.flush();
    }

    @Test
    public void findByOrderIdTest() {
        // Given
        int orderId = 1;

        // When
        List<OrderItem> result = orderItemRepository.findByOrderId(orderId);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(orderId);
        assertThat(result.get(0).getTotalPrice()).isEqualTo(50000);
    }
}