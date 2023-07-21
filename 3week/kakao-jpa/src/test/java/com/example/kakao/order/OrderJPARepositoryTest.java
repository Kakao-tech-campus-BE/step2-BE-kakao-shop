package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;



@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private EntityManager em;


    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("ssar"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        Option option = optionJPARepository.findById(1).get();
        Cart cart = cartJPARepository.save(newCart(user,option, 5));
        Order order = orderJPARepository.save(newOrder(user));

        em.clear();
    }

    @Test
    public void addOrder_test() throws JsonProcessingException { // 10. 결제하기
        //give

        //when
        User user = newUser("ssar2");
        Order order = orderJPARepository.save(newOrder(user));
        Optional<Order> findOrder = orderJPARepository.findById(2);

        //then
        Assertions.assertThat(findOrder.get().getId()).isEqualTo(order.getId());
        Assertions.assertThat(findOrder.get().getUser().getUsername()).isEqualTo(order.getUser().getUsername());
    }

    @Test
    public void order_findByUserId_test() throws JsonProcessingException { // 11. 주문 결과 확인 by userID
        //given
        int userId = 1;

        //when
        List<Order> orderPSList = orderJPARepository.findByUserId(userId);

        //then
        Assertions.assertThat(orderPSList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderPSList.get(0).getUser().getId()).isEqualTo(userId);
    }

    @Test
    public void order_findByOrderId_test() throws JsonProcessingException{ // bt orderID
        //given
        int orderId = 1;

        //when
        Order order = orderJPARepository.findById(orderId).get();

        //then
        Assertions.assertThat(order.getId()).isEqualTo(orderId);
    }
}
