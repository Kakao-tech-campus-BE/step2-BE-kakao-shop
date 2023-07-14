package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
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


    @DisplayName("주문하기 테스트")
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

    // 주문 삭제, 수정은 없다.

}