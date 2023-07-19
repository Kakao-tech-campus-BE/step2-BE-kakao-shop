package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
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

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("moly"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());

        List<Option> optionListPS = optionDummyList(productListPS);
        optionJPARepository.saveAll(optionListPS);

        List<Cart> cartListPS = cartDummyList(user, optionListPS);

        Order order = newOrder(user);
        orderJPARepository.save(order);

        itemJPARepository.saveAll(itemDummyList(order, cartListPS));

        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        em.clear();
    }

    @Test
    @DisplayName("상세 주문 내역 확인")
    public void order_mFindById_test() throws JsonProcessingException {
        // given
        int orderId = 1;

        // when
        Order order = orderJPARepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("해당하는 주문이 없습니다.")
        );

        List<Item> itemListPS = itemJPARepository.mFindAllByOrderId(orderId);

        System.out.println("json 직렬화 직전========================");
        String responseBody1 = om.writeValueAsString(order);
        String responseBody2 = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : " + responseBody1);
        System.out.println("테스트 : " + responseBody2);

        //then
        Assertions.assertThat(order.getId()).isEqualTo(1);
        Assertions.assertThat(order.getUser().getUsername()).isEqualTo("moly");

        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOrder().getUser().getUsername()).isEqualTo("moly");
    }


    @Test
    @DisplayName("주문 목록 확인")
    public void order_findAllByUserId_test() throws JsonProcessingException {

        // given
        int userId = 1;

        // when
        List<Order> orderListPS = orderJPARepository.findAllByUserId(userId);

        System.out.println("json 직렬화 직전========================");
        String responseBody1 = om.writeValueAsString(orderListPS);
        System.out.println("테스트 : " + responseBody1);

        //then
        Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("moly");
    }
}
