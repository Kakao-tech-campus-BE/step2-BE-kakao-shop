package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ObjectMapper.class)
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    private User user;

    private Order order;

    private Item item;

    private Option option;

    private Cart cart;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        user = newUser("보쨩");
        userJPARepository.save(user);

        option = optionDummyList(productDummyList()).get(0);
        optionJPARepository.save(option);

        cart = newCart(user, option, 5);
        cartJPARepository.save(cart);

        em.clear();
    }
    @Test
    public void orderItem_save_test(){ // option, order -> LAZY 전략으로(지연 로딩만 사용)
        //given
        order = newOrder(user);
        item = newItem(cart, order);
        //when
        orderJPARepository.save(order);
        Item savedOrderItem = itemJPARepository.save(item);
        //then
        assertThat(savedOrderItem.getOrder().getId()).isEqualTo(order.getId());
        assertThat(savedOrderItem.getOption().getOptionName()).isEqualTo(option.getOptionName());
        assertThat(savedOrderItem.getQuantity()).isEqualTo(cart.getQuantity());
        assertThat(savedOrderItem.getPrice()).isEqualTo(cart.getPrice());
    }

    @Test
    public void orderItem_findById_test(){//id 로 주문조회
        //given
        int id = 1;

        order = newOrder(user);
        item = newItem(cart, order);

        orderJPARepository.save(order);
        itemJPARepository.save(item);

        //when
        Optional<Item> orderItemById = itemJPARepository.findById(id);

        //then
        assertThat(orderItemById.get().getOrder().getId()).isEqualTo(order.getId());
        assertThat(orderItemById.get().getOption().getOptionName()).isEqualTo(option.getOptionName());
        assertThat(orderItemById.get().getQuantity()).isEqualTo(cart.getQuantity());
        assertThat(orderItemById.get().getPrice()).isEqualTo(cart.getPrice());
    }
}
