package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

@DataJpaTest
@Import(ObjectMapper.class)
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserJPARepository userJPARepository;
    @BeforeEach
    public void setUp() {
        em.clear();
        User user = newUser("dlwlsgur");
        userJPARepository.save(user);
        Order order = newOrder(user);
        orderJPARepository.save(order);
    }
    @Test
    public void order_findByUserId() throws JsonProcessingException {

            //given
            int userId = 1;

            //when
            Order orderPS = orderJPARepository.findByUserId(userId).orElseThrow(
                    () -> new RuntimeException("유저를 찾을 수 없습니다")
            );

            String responseBody1 = om.writeValueAsString(orderPS);
            System.out.println("테스트 : " + responseBody1);
            em.close();

            //then
        Assertions.assertThat(orderPS.getId()).isEqualTo(1);
        Assertions.assertThat(orderPS.getUser().getUsername()).isEqualTo("dlwlsgur");
    }
}
