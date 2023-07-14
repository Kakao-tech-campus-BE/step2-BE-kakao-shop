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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    private final UserJPARepository userJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final EntityManager entityManager;

    public OrderJPARepositoryTest(
            @Autowired UserJPARepository userJPARepository,
            @Autowired OrderJPARepository orderJPARepository,
            @Autowired ItemJPARepository itemJPARepository,
            @Autowired CartJPARepository cartJPARepository,
            @Autowired OptionJPARepository optionJPARepository,
            @Autowired EntityManager entityManager
    ) {
        this.userJPARepository = userJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.itemJPARepository = itemJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("ssar"));
        List<Option> options = optionJPARepository.saveAll(optionDummyList(productDummyList()));
        List<Cart> carts = cartJPARepository.saveAll(
                Arrays.asList(
                        newCart(user, options.get(0), 5), 
                        newCart(user, options.get(2), 3)
                )
        );
        Order order = orderJPARepository.save(
                Order.builder()
                        .user(user)
                        .build()
        );
        itemJPARepository.saveAll(
                Arrays.asList(
                        newItem(carts.get(0), order),
                        newItem(carts.get(1), order)
                )
        );
        entityManager.clear();
    }

    @DisplayName("insert order test")
    @Test
    public void insertTest() {
        // given
        User user = userJPARepository.findById(1).orElseThrow();
        Option option = optionJPARepository.findById(11).orElseThrow();
        Cart cart = cartJPARepository.save(newCart(user, option, 10));
        long previous_order_count = orderJPARepository.count();
        long previous_item_count = itemJPARepository.count();

        // when
        Order order = orderJPARepository.save(
                Order.builder()
                        .user(user)
                        .build()
        );
        Item item = itemJPARepository.save(newItem(cart, order));

        // then
        assertThat(orderJPARepository.count()).isEqualTo(previous_order_count + 1);
        assertThat(itemJPARepository.count()).isEqualTo(previous_item_count + 1);
        assertThat(order)
                .extracting("user")
                .extracting("id")
                .isEqualTo(user.getId());
        assertThat(item)
                .extracting("order")
                .extracting("id")
                .isEqualTo(order.getId());
    }

    @DisplayName("find all order items using order-id test")
    @Test
    public void findAllByUserIdTest() {
        // given
        Order order = orderJPARepository.findById(1).orElseThrow();

        // when
        List<Item> items = itemJPARepository.findAllByOrderId(order.getId());

        // then
        assertThat(items).hasSize(2);
    }
}
