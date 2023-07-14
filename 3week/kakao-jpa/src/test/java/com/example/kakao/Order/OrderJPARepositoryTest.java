package com.example.kakao.Order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import javax.persistence.EntityManager;
import java.util.List;


@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private EntityManager em;


    private User user;

    @BeforeEach
    public void setUp(){
        user = newUser("jiyoon");
        userJPARepository.save(user);
        orderJPARepository.save(newOrder(user));
        em.clear();
    }

    @Test //유저아이디로 주문 검색
    public void order_findOrderByUserId_test(){
        //given
        int userId=1;
        //when
        List<Order> findOrder=orderJPARepository.findByUserId(userId);
        //then
        Assertions.assertThat(findOrder.get(0).getUser().getId()).isEqualTo(userId);
    }

    @Test //주문번호로 주문 검색
    public void order_findOrderById_test(){
        //given
        int id=1;
        //when
        Order findOrder=orderJPARepository.findById(id).get();
        //then
        Assertions.assertThat(findOrder.getId()).isEqualTo(id);
        Assertions.assertThat(findOrder.getUser().getUsername()).isEqualTo("jiyoon");
    }
}