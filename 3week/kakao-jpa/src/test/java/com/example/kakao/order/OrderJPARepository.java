package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.item.Order;
import com.example.kakao.item.item.Item;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
public class OrderJPARepository extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private com.example.kakao.item.OrderJPARepository orderJPARepository;

    @BeforeEach
    public void setUp() {
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User userPS = userJPARepository.save(newUser("cha"));
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        Order orderPS = orderJPARepository.save(newOrder(userPS));
        em.clear();
    }


    @Test
    public void order_save_test() throws JsonProcessingException {
        // given
        String username = "abc";

        // when
        List<Product> productListPS = productJPARepository.findAll();
        List<Option> optionListPS = optionJPARepository.findAll();
        User userPS = userJPARepository.save(newUser(username));
        List<Cart> cartListPS = cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        Order orderPS = orderJPARepository.save(newOrder(userPS));

        List<Order> orderListPS = orderJPARepository.findAll();

        // then
        Assertions.assertThat(orderListPS.size()).isEqualTo(2);
    }


    @Test
    public void order_findById_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Order order = orderJPARepository.findById(id).get();

        // then
        Assertions.assertThat(order.getId()).isEqualTo(id);
    }

    @Test
    public void item_findByOrderId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Order orderPS = orderJPARepository.findByUserId(id).get();

        // then
        Assertions.assertThat(orderPS.getUser().getId()).isEqualTo(id);
    }

    @Test
    public void item_deleteById_test() throws JsonProcessingException {
        // given

        // when

        // then

    }

}
