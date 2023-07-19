package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    private User user;

    private Order order;

    @BeforeEach
    public void setUp(){
        user = userJPARepository.save(newUser("ssar"));
        order = orderJPARepository.save(newOrder(user));
        em.clear();
    }

    @DisplayName("사용자별 주문 조회")
    @Test
    public void order_findByUserId_test() throws JsonProcessingException {
        // given
        int userId = user.getId();

        // when
        List<Order> orderList = orderJPARepository.findByUserId(userId);

        // then
        Assertions.assertThat(orderList.get(0).getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThat(orderList.get(0).getId()).isEqualTo(order.getId());
    }

    @DisplayName("주문 id별 주문 조회")
    @Test
    public void order_findById_test() throws JsonProcessingException {
        // given
        int orderId = order.getId();

        // when
        Optional<Order> retrievedOrder = orderJPARepository.findById(orderId);
        if (retrievedOrder.isPresent()) {
            Order myorder = retrievedOrder.get();

            // then
            Assertions.assertThat(myorder.getUser().getId()).isEqualTo(user.getId());
            Assertions.assertThat(myorder.getId()).isEqualTo(order.getId());
        }
    }
}
