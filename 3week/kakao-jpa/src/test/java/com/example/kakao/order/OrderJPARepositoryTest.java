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
import java.util.Arrays;
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

        User user = userJPARepository.save(newUser("user"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));

        List<Cart> carts = cartJPARepository.saveAll(Arrays.asList(
                newCart(user, options.get(3), 2),   // cartId:1 - optionId:4 - productId:1
                newCart(user, options.get(5), 5)    // cartId:2 - optionId:6 - productId:2
        ));

        Order order = orderJPARepository.save(newOrder(user));

        List<Item> items = itemJPARepository.saveAll(Arrays.asList(
                newItem(carts.get(0), order),   // itemId:1
                newItem(carts.get(1), order)    // itemId:2
        ));

        em.clear();
    }

    @DisplayName("주문하기")
    @Test
    public void order_save_test() { // save order3
        // given
        User user = userJPARepository.save(newUser("user2"));  // userId:2
        Option option = optionJPARepository.findById(22).orElseThrow(); // optionId:22 - productId:6
        Cart cart = cartJPARepository.save(newCart(user, option, 1));   // cartId:3
        Order order = orderJPARepository.save(newOrder(user));  // orderId:2
        Item item = itemJPARepository.save(newItem(cart, order)); // itemId:3

        Assertions.assertThat(order.getId()).isEqualTo(2);
        Assertions.assertThat(order.getUser().getId()).isEqualTo(2);

        Assertions.assertThat(item.getId()).isEqualTo(3);
        Assertions.assertThat(item.getOrder().getId()).isEqualTo(2);
        Assertions.assertThat(item.getOrder().getUser().getId()).isEqualTo(2);
        Assertions.assertThat(item.getOption().getProduct().getId()).isEqualTo(6);
        Assertions.assertThat(item.getOption().getId()).isEqualTo(22);
    }

    @DisplayName("주문 단건 상세 조회")
    @Test
    public void order_findItemsByOrderId_test() {
        int orderId = 1;
        List<Item> items = itemJPARepository.findByOrderId(orderId);

        Assertions.assertThat(items.size()).isEqualTo(2);   // itemId: 1, 2

        Assertions.assertThat(items.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getOrder().getId()).isEqualTo(orderId);
        Assertions.assertThat(items.get(0).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getOption().getId()).isEqualTo(4);
        Assertions.assertThat(items.get(0).getOption().getProduct().getId()).isEqualTo(1);

        Assertions.assertThat(items.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(items.get(1).getOrder().getId()).isEqualTo(orderId);
        Assertions.assertThat(items.get(1).getOrder().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(1).getOption().getId()).isEqualTo(6);
        Assertions.assertThat(items.get(1).getOption().getProduct().getId()).isEqualTo(2);
    }

    @DisplayName("전체 주문내역 목록 조회")
    @Test
    public void order_findAllByUserId_test() {
        int userId = 1;
        List<Order> orders = orderJPARepository.findByUserId(userId);

        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orders.get(0).getUser().getId()).isEqualTo(userId);
    }

}
