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
    private ObjectMapper om;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @BeforeEach
    public void setUp(){
        User ssar = userJPARepository.save(newUser("ssar"));
        Order order = newOrder(ssar);
        orderJPARepository.save(order);
        em.clear();
    }

    @DisplayName("사용자별 주문 조회")
    @Test
    public void order_findByUserId_test() throws JsonProcessingException {
        // given
        int userId = 1;

        // when
        List<Order> orderList = orderJPARepository.findByUserId(userId);
        System.out.println("사용자별 주문 조회========================");
        String responseBody = om.writeValueAsString(orderList);
        System.out.println("결과값 : " +responseBody);

        // then
        Assertions.assertThat(orderList.get(0).getUser()).isEqualTo(new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
        Assertions.assertThat(orderList.get(0).getId()).isEqualTo(1);
    }
    @DisplayName("주문 id별 주문 조회")
    @Test
    public void order_findByOrderId_test() throws JsonProcessingException {
        // given
        int userId = 1;

        // when
        Optional<Order> order = orderJPARepository.findByOrderId(userId);
        if (order.isPresent()) {
            Order myorder = order.get();
            System.out.println("주문 id별 주문 조회========================");
            String responseBody = om.writeValueAsString(myorder);
            System.out.println("결과값 : " + responseBody);

            // then
            Assertions.assertThat(myorder.getUser()).isEqualTo(new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
            Assertions.assertThat(myorder.getId()).isEqualTo(1);
        }
    }
}
