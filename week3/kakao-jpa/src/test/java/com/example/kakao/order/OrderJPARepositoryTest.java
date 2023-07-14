package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    private final UserJPARepository userJPARepository;
    private final EntityManager em;
    private final ObjectMapper om;
    private final OrderJPARepository orderJPARepository;

    @Autowired
    public OrderJPARepositoryTest(UserJPARepository userJPARepository, EntityManager em, ObjectMapper om, OrderJPARepository orderJPARepository) {
        this.userJPARepository = userJPARepository;
        this.em = em;
        this.om = om;
        this.orderJPARepository = orderJPARepository;
    }

//    @Autowired
//    public OrderJPARepositoryTest(UserJPARepository userJPARepository, ProductJPARepository productJPARepository, EntityManager em, ObjectMapper om, ItemJPARepositoryTest itemJPARepository, CartJPARepository cartJPARepository, OptionJPARepository optionJPARepository, OrderJPARepository orderJPARepository, User user) {
//        this.userJPARepository = userJPARepository;
//        this.productJPARepository = productJPARepository;
//        this.em = em;
//        this.om = om;
//        this.itemJPARepository = itemJPARepository;
//        this.cartJPARepository = cartJPARepository;
//        this.optionJPARepository = optionJPARepository;
//        this.orderJPARepository = orderJPARepository;
//        this.user = user;
//    }

    private User user = newUser("han");

    @BeforeEach
    public void setUp(){
        userJPARepository.save(user);
        orderJPARepository.save(newOrder(user));
        em.clear();
    }

    @Test
    @DisplayName("유저아이디로 주문 조회")
    public void order_findOrderByUserId(){
        int userId = 1;
        List<Order> searchOrder = orderJPARepository.findByUserId(userId);
    }

    @Test
    @DisplayName("주문아이디로 주문 조회")
    public void order_findByOrderID(){
        int id = 1;
        List<Order> searchOrder = orderJPARepository.findByOrderId(id);
    }




}
