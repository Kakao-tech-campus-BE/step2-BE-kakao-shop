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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DisplayName("주문_주문상품_Repository_테스트")
//@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    private final EntityManager em;
    private final ItemJPARepository itemJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ProductJPARepository productJPARepository;
    private final UserJPARepository userJPARepository;

    @Autowired
    public OrderJPARepositoryTest(EntityManager em, ItemJPARepository itemJPARepository, OrderJPARepository orderJPARepository, CartJPARepository cartJPARepository, OptionJPARepository optionJPARepository, ProductJPARepository productJPARepository, UserJPARepository userJPARepository) {
        this.em = em;
        this.itemJPARepository = itemJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("ssar"));
        User user2 = userJPARepository.save(newUser("ssar2"));
        Order order = orderJPARepository.save(newOrder(user));
        Order order2 = orderJPARepository.save(newOrder(user));
        Order order3 = orderJPARepository.save(newOrder(user2));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = cartJPARepository.saveAll(cartDummyList(options, user));
        List<Cart> carts2 = cartJPARepository.saveAll(cartDummyList2(options, user));
        List<Cart> carts3 =  cartJPARepository.saveAll(cartDummyList(options, user2));
        itemJPARepository.saveAll(itemDummyList(carts, order));
        itemJPARepository.saveAll(itemDummyList(carts2, order2));
        itemJPARepository.saveAll(itemDummyList(carts3, order3));
        em.clear();
    }

    @DisplayName("주문_세이브_성공_테스트")
    @Test
    public void order_save_test() {
        // given
        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원은 존재하지 않습니다."));
        Order order = newOrder(user);

        // when
        orderJPARepository.save(order);

        // then
        assertThat(order.getId()).isNotEqualTo(0);
    }

    @DisplayName("주문_세이브_실패_유저_데이터_없음")
    @Test
    public void order_save_fail_no_user_test() {
        // given
        Order order = newOrder(null);

        // when then
        assertThatThrownBy(() -> orderJPARepository.save(order))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("주문_회원ID로_찾기_테스트_성공")
    @Test
    public void order_findByUserId_test() {
        // given
        int userId = 1;

        // when
        List<Order> orderList = orderJPARepository.findByUserId(userId);

        // then
        assertThat(orderList.size()).isEqualTo(2);
        assertThat(orderList.get(0).getId()).isEqualTo(1);
        assertThat(orderList.get(0).getUser().getId()).isEqualTo(1);
        assertThat(orderList.get(0).getUser().getUsername()).isEqualTo("ssar");
        assertThat(orderList.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
        assertThat(orderList.get(1).getId()).isEqualTo(2);
        assertThat(orderList.get(1).getUser().getId()).isEqualTo(1);
        assertThat(orderList.get(1).getUser().getUsername()).isEqualTo("ssar");
        assertThat(orderList.get(1).getUser().getEmail()).isEqualTo("ssar@nate.com");
    }


    @DisplayName("주문_회원ID로_찾기_테스트_실패_데이터_없음")
    @Test
    public void order_findByUserId_test_fail_no_data_test() {
        // given
        int userId = 100;

        // when
        List<Order> orderList = orderJPARepository.findByUserId(userId);

        // then
        assertThat(orderList).isEqualTo(List.of());
    }

    @DisplayName("주문상품_저장_성공")
    @Test
    public void item_save_success_test() {
        // given
        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Option option = optionJPARepository.findById(30).orElseThrow(() -> new RuntimeException("해당 옵션이 존재하지 않습니다."));
        Order order = orderJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다."));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Item item = newItem(cart, order);

        // when
        Item itemResult = itemJPARepository.save(item);

        // then
        assertThat(itemResult.getId()).isNotEqualTo(0);
    }

    @DisplayName("주문상품_저장_실패_옵션_없음")
    @Test
    public void item_save_fail_no_cart_data_test() {
        // given
        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Option option = optionJPARepository.findById(30).orElseThrow(() -> new RuntimeException("해당 옵션이 존재하지 않습니다."));
        Order order = orderJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 주문이 존재하지 않습니다."));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Item item = Item.builder().quantity(0).price(0).option(null).order(order).build();

        // when then
        assertThatThrownBy(() -> itemJPARepository.save(item))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("주문상품_저장_실패_주문_없음")
    @Test
    public void item_save_fail_no_order_data_test() {
        // given
        User user = userJPARepository.findById(1).orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Option option = optionJPARepository.findById(30).orElseThrow(() -> new RuntimeException("해당 옵션이 존재하지 않습니다."));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Item item = newItem(cart, null);

        // when then
        assertThatThrownBy(() -> itemJPARepository.save(item))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("주문상품_저장_실패_주문_옵션_중복")
    @Test
    public void item_save_fail_duplicate_order_option_test() {
        // given
        Cart cart = cartJPARepository.findById(1).orElseThrow(() -> new RuntimeException("존재하지 않는 장바구니입니다."));
        Order order = orderJPARepository.findById(1).orElseThrow(() -> new RuntimeException("존재하지 않는 주문입니다."));
        Item item = newItem(cart, order);

        // when then
        assertThatThrownBy(() -> itemJPARepository.save(item))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("주문상품_findByOrderId_성공")
    @Test
    public void item_findByOrderId_success_test() {
        // given
        int orderId = 1;
        // when

        List<Item> itemList = itemJPARepository.findByOrderId(orderId);

        // then
        assertThat(itemList.size()).isEqualTo(4);
        assertThat(itemList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(itemList.get(1).getOption().getId()).isEqualTo(2);
        assertThat(itemList.get(2).getOption().getId()).isEqualTo(16);
        assertThat(itemList.get(3).getOption().getId()).isEqualTo(17);
        assertThat(itemList.get(0).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(1).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(2).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(3).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(itemList.get(1).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(itemList.get(2).getOption().getProduct().getId()).isEqualTo(4);
        assertThat(itemList.get(3).getOption().getProduct().getId()).isEqualTo(4);
    }

    @DisplayName("주문상품_findByUserId_성공")
    @Test
    public void item_findByUserId_success_test() {
        // given
        int userId = 1;

        // when
        List<Item> itemList = itemJPARepository.findByUserId(userId);

        // then
        assertThat(itemList.size()).isEqualTo(6);
        assertThat(itemList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(itemList.get(1).getOption().getId()).isEqualTo(2);
        assertThat(itemList.get(2).getOption().getId()).isEqualTo(16);
        assertThat(itemList.get(3).getOption().getId()).isEqualTo(17);
        assertThat(itemList.get(4).getOption().getId()).isEqualTo(3);
        assertThat(itemList.get(5).getOption().getId()).isEqualTo(4);
        assertThat(itemList.get(0).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(1).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(2).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(3).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(4).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(5).getOrder().getUser().getId()).isEqualTo(1);
        assertThat(itemList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(itemList.get(1).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(itemList.get(2).getOption().getProduct().getId()).isEqualTo(4);
        assertThat(itemList.get(3).getOption().getProduct().getId()).isEqualTo(4);
        assertThat(itemList.get(4).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(itemList.get(5).getOption().getProduct().getId()).isEqualTo(1);
    }

    // 판매자를 위한 쿼리
    @DisplayName("주문상품_findByProductId_성공")
    @Test
    public void item_findByProductId_success_test() {
        // given
        int productId = 1;

        // when
        List<Item> itemList = itemJPARepository.findByProductId(productId);

        // 사용자 별 제품구매 총 가격 (Repository 테스트와는 거리가 멀지만 심심해서 넣어봤습니다..)
        Map<String, Integer> itemProductSumMap = itemList.stream()
                .collect(Collectors.groupingBy(i -> i.getOrder().getUser().getUsername(),
                        Collectors.summingInt(Item::getPrice)));

        Long userCount = itemList.stream()
                .map((Item i) -> i.getOrder().getUser().getId()).distinct()
                .count();

        // then
        assertThat(itemList.size()).isEqualTo(6);
        assertThat(userCount).isEqualTo(2L);
        assertThat(itemProductSumMap.get("ssar")).isEqualTo(203600);
        assertThat(itemProductSumMap.get("ssar2")).isEqualTo(52700);
    }

    // 판매자를 위한 쿼리
    @DisplayName("주문상품_findByOptionID_성공")
    @Test
    public void item_findByOptionId_success_test() {
        // given
        int optionId = 1;

        // when
        List<Item> itemList = itemJPARepository.findByOptionId(optionId);
        Long userCount = itemList.stream()
                .map(i -> i.getOrder().getUser().getId()).distinct()
                .count();

        int optionTotalPrice = itemList.stream()
                .mapToInt(Item::getPrice)
                .sum();

        // then
        assertThat(itemList.size()).isEqualTo(2);
        assertThat(userCount).isEqualTo(2);
        assertThat(optionTotalPrice).isEqualTo(40000);
    }
}
