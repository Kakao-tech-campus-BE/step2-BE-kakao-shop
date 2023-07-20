package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("han"));
        orderJPARepository.save(newOrder(user));
        em.clear();
    }

    @AfterEach
    public void close() {
        em.clear();
        userJPARepository.deleteAll();
        orderJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
    }

    @Test
    @DisplayName("유저아이디로 주문 조회")
    public void order_findOrderByUserId(){
        //given
        int userId = 1;
        //when
        List<Order> searchOrder = orderJPARepository.findByUserId(userId);
        //then
        Assertions.assertThat(searchOrder.get(0).getUser().getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("주문아이디로 주문 조회")
    public void order_findByOrderID(){
        //when
        int id = 1;
        //given
        List<Order> searchOrder = orderJPARepository.findByOrderId(id);
        //then
        Assertions.assertThat(searchOrder.get(0).getId()).isEqualTo(id);
    }

}