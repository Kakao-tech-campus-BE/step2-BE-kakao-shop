package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;


@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD) // 클래스의 모든 테스트 케이스마다 시작하기 이전에 context 재생성
class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;


    @BeforeEach
    @DisplayName("더미 주문 준비")
    public void setUp(){
        User user = newUser("heechan");
        userJPARepository.save(user);

        Order order = newOrder(user);
        orderJPARepository.save(order);

        em.clear(); // 영속성 컨텍스트 비우기
    }


    @DisplayName("주문 생성")
    @Test
    void order_insert_test() {

        // given
        int userId = 1;
        User user = userJPARepository.findById(userId).orElseThrow();
        Order order = newOrder(user);
        int beforeOrderSize = orderJPARepository.findByUserId(userId).size();

        // when
        orderJPARepository.save(order);

        // then
        assertThat(orderJPARepository.findByUserId(user.getId())).hasSize(beforeOrderSize + 1);
    }


    @DisplayName("pk로 주문한 유저 조회")
    @Test
    void order_selectById_test() {

        // given
        int orderId = 1;

        // when
        Order order = orderJPARepository.findById(orderId).orElseThrow();

        // then
        assertThat(order.getUser().getUsername()).isEqualTo("heechan");
    }


    @DisplayName("유저id로 주문 내역 조회")
    @Test
    void order_selectByUserId_test() {

        // given
        int userId = 1;

        // when
        List<Order> orders = orderJPARepository.findByUserId(userId);

        // then
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getUser().getUsername()).isEqualTo("heechan");
        assertThat(orders.get(0).getUser().getId()).isEqualTo(userId);
    }

}