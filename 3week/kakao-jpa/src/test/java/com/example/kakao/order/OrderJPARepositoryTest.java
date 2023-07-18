package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;



import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @BeforeEach
    public void setup() {
        String userName = "ssar";
        User user = userJPARepository.save(newUser(userName));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productListPS));
        Cart cart = cartJPARepository.save(newCart(user, optionList.get(0), 10));
        Order order = orderJPARepository.save(newOrder(user));
        Item item = itemJPARepository.save(newItem(cart, order));
        em.clear();
    }


    @Test
    void find_order_test() {
        //given
        int orderId = 1;

        //when
        Optional<Order> order = orderJPARepository.findById(orderId);
        em.clear();

        //then
        Assertions.assertThat(order.get().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(order.get().getId()).isEqualTo(orderId);
    }

    @Test
    void find_item_test() {
        //given
        int orderId = 1;

        //when
        List<Item> item = itemJPARepository.findItemByOrderId(orderId);

        //then
        Assertions.assertThat(item.get(0).getOption().getId()).isEqualTo(1);

    }
}