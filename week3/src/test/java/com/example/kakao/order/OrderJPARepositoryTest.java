package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        userJPARepository.save(userDummy); //유저 저장
        orderJPARepository.saveAll(orderDummyList()); //주문 리스트 저장
        itemJPARepository.saveAll(itemDummyList(cartDummyList(optionDummyList(productDummyList())))); //아이템 리스트 저장
        em.clear(); //쿼리 보기 위해 PC clear
    }

    @Test
    @DisplayName("1. 주문 결과 확인")
    public void order_findAll_test() throws JsonProcessingException {
        // given

        // when
        //select 쿼리 1번(join fetch)
        List<Order> orderList = orderJPARepository.FindFetchAllWithUser(); //join fetch하여 연관관계의 객체 가져오기
        String responseBody = om.writeValueAsString(orderList); //직렬화하여 출력
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(orderList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderList.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderList.get(0).getUser().getEmail()).isEqualTo("user1@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", orderList.get(0).getUser().getPassword()));
        Assertions.assertThat(orderList.get(0).getUser().getUsername()).isEqualTo("user1");
        Assertions.assertThat(orderList.get(0).getUser().getRoles()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("2.결제하기 (주문 저장)")
    public void order_save_test() throws JsonProcessingException {
        // given
        Order order = newOrder(userDummy); //주문 생성

        // when
        //insert 쿼리 1번
        orderJPARepository.save(order); //주문 저장

        // then
        Assertions.assertThat(order.getId()).isEqualTo(2);
        Assertions.assertThat(order.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(order.getUser().getEmail()).isEqualTo("user1@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", order.getUser().getPassword()));
        Assertions.assertThat(order.getUser().getUsername()).isEqualTo("user1");
        Assertions.assertThat(order.getUser().getRoles()).isEqualTo("ROLE_USER");
    }
}
