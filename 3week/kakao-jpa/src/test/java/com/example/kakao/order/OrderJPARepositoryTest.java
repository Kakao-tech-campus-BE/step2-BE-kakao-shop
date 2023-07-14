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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
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
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        // save order1
        User user1 = userJPARepository.save(newUser("user_order"));    // userId: 1
        List<Product> order1_products = productJPARepository.saveAll(productDummyList()); // productId: 1
        List<Option> order1_options = optionJPARepository.saveAll(optionDummyList(order1_products)); // optionId: 1,2
        List<Cart> order1_carts = cartJPARepository.saveAll(cartDummyList(user1, order1_options));
        Order order1 = orderJPARepository.save(newOrder(user1));                // orderId: 1
        List<Item> order1_items = itemJPARepository.saveAll(itemDummyList(order1, order1_carts));    // itemId: 1, 2

        // save order2
        Product order2_product = productJPARepository.getById(3);   // productId: 3
        Option order2_option = optionJPARepository.getById(9);      // optionId: 9
        Cart order2_cart = cartJPARepository.save(newCart(user1, order2_option, 1));
        Order order2 = orderJPARepository.save(newOrder(user1));    // orderId: 2
        Item order2_item = itemJPARepository.save(newItem(order2_cart, order2));    // itemId: 3

        em.clear();
    }

    @DisplayName("주문하기")
    @Test
    public void order_save_test() { // save order3
        // given
        User user2 = userJPARepository.save(newUser("user2"));
        Product order3_product = productJPARepository.getById(6); // productId: 6
        Option order3_option = optionJPARepository.getById(22); // optionId: 22
        Cart order3_cart = cartJPARepository.save(newCart(user2, order3_option, 1));

        // when
        Order order3 = orderJPARepository.save(newOrder(user2));  // orderId: 3
        Item order3_item = itemJPARepository.save(newItem(order3_cart, order3)); // itemId: 4

        // then
        Assertions.assertThat(order3.getId()).isEqualTo(3);
        Assertions.assertThat(order3.getUser().getId()).isEqualTo(2);

        Assertions.assertThat(order3_item.getId()).isEqualTo(4);
        Assertions.assertThat(order3_item.getOrder().getId()).isEqualTo(3);
        Assertions.assertThat(order3_item.getOrder().getUser().getId()).isEqualTo(2);
        Assertions.assertThat(order3_item.getOption().getProduct().getId()).isEqualTo(6);
        Assertions.assertThat(order3_item.getOption().getId()).isEqualTo(22);
    }

    @DisplayName("주문 단건 상세 조회")
    @Test
    public void order_findItemsByOrderId_test() {
        // given
        int order1_id = 1;
        int order2_id = 2;

        // when
        List<Item> order1_items = itemJPARepository.findByOrderId(order1_id);
        List<Item> order2_items = itemJPARepository.findByOrderId(order2_id);

        // then
        // get order1(item 1, 2)
        Assertions.assertThat(order1_items.size()).isEqualTo(2);
        Assertions.assertThat(order1_items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(order1_items.get(0).getOrder().getId()).isEqualTo(order1_id);
        Assertions.assertThat(order1_items.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(order1_items.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(order1_items.get(0).getOption().getProduct().getId()).isEqualTo(1);

        Assertions.assertThat(order1_items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(order1_items.get(1).getOrder().getId()).isEqualTo(order1_id);
        Assertions.assertThat(order1_items.get(1).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(order1_items.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(order1_items.get(1).getOption().getProduct().getId()).isEqualTo(1);

        // get order2 (item 3)
        Assertions.assertThat(order2_items.size()).isEqualTo(1);

        Assertions.assertThat(order2_items.get(0).getId()).isEqualTo(3);
        Assertions.assertThat(order2_items.get(0).getOrder().getId()).isEqualTo(order2_id);
        Assertions.assertThat(order2_items.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(order2_items.get(0).getOption().getId()).isEqualTo(9);
        Assertions.assertThat(order2_items.get(0).getOption().getProduct().getId()).isEqualTo(3);
    }

    @DisplayName("전체 주문내역 목록 조회")
    @Test
    public void order_findAllByUserId_test() {
        // given
        int user1_id = 1;

        // when
        List<Order> user1_orders = orderJPARepository.findByUserId(user1_id);

        //then
        // user1 orders
        Assertions.assertThat(user1_orders.size()).isEqualTo(2);
        Assertions.assertThat(user1_orders.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(user1_orders.get(0).getUser().getId()).isEqualTo(user1_id);
        Assertions.assertThat(user1_orders.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(user1_orders.get(1).getUser().getId()).isEqualTo(user1_id);

    }

}
