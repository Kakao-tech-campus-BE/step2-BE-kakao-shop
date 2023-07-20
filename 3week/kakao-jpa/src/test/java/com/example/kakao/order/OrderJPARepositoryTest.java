package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.OrderItem;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity{

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp() {
        // 테스트에 필요한 엔티티 생성 및 저장
        User user1 = newUser("ssar");
        userJPARepository.save(user1);

        Order order1 = newOrder(user1);

        orderJPARepository.save(order1);
    }

    @AfterEach
    public void clearPersistenceContext() {
        entityManager.clear();
    }

    @Test
    public void shouldReturnOrdersByUser() {

        // given
        String email = "ssar@nate.com";

        // when
        User user = userJPARepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
        List<Order> orders = orderJPARepository.findByUser(user);

        // then
        assertEquals(1, orders.size());
    }
}