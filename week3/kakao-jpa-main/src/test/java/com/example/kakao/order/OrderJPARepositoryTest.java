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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
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

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User userA = newUser("userA");
        User userB = newUser("userB");
        userJPARepository.saveAll(Arrays.asList(userA, userB));

        List<Product> products = productDummyList();
        productJPARepository.saveAll(products);

        List<Option> options = optionDummyList(products);
        optionJPARepository.saveAll(options);

        List<Cart> cartsA = cartDummyList(userA, options, Arrays.asList(1, 2));
        List<Cart> cartsB = cartDummyList(userB, options, Arrays.asList(6, 7));
        cartJPARepository.saveAll(cartsA);
        cartJPARepository.saveAll(cartsB);

        Order orderA = newOrder(userA);
        Order orderB = newOrder(userB);
        orderJPARepository.saveAll(Arrays.asList(orderA, orderB));

        itemJPARepository.saveAll(itemDummyList(cartsA, orderA));
        itemJPARepository.saveAll(itemDummyList(cartsB, orderB));

        em.clear();
    }

    @AfterEach
    void clear(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    @DisplayName("전체 주문 내역 조회")
    void order_findAll_test(){
        //given
        log.info(">> order_findAll_test");

        //when
        List<Order> findAllList = orderJPARepository.findAll();

        //then
        assertThat(findAllList).hasSize(2);
    }

    @Test
    @DisplayName("주문 번호로 주문 아이템 조회 성공")
    void item_findByOrderId_test() {
        //given
        log.info(">> item_findByOrderId_test");
        int orderId = 2;

        //when
        List<Item> findByIdList = itemJPARepository.findByOrderId(orderId);

        //then
        assertThat(findByIdList).hasSize(2);
        assertThat(findByIdList.get(0).getId()).isEqualTo(3); //itemId
        assertThat(findByIdList.get(0).getOrder().getId()).isEqualTo(2); //orderId
        assertThat(findByIdList.get(0).getOrder().getUser().getId()).isEqualTo(2); //userId
        assertThat(findByIdList.get(0).getOption().getId()).isEqualTo(6); //optionId
        assertThat(findByIdList.get(0).getOption().getProduct().getId()).isEqualTo(2); //productId
        assertThat(findByIdList.get(0).getPrice()).isEqualTo(9900);
    }

    @Test
    @DisplayName("주문 번호로 주문 아이템 조회 실패")
    void item_findByOrderId_fail_test() {
        //given
        log.info(">> item_findByOrderId_fail_test");
        int orderId = 100;

        //when
        List<Item> findByIdList = itemJPARepository.findByOrderId(orderId);

        //then
        assertThat(findByIdList).isEmpty();
    }

    @Test
    @DisplayName("주문 번호로 주문 아이템 조회 - fetch join")
    void item_findByOrderId_joinFetch_test() {
        //given
        log.info(">> item_findByOrderId_joinFetch_test");
        int orderId = 1;

        //when
        List<Item> findByOrderIdList = itemJPARepository.findByOrderIdJoinFetch(orderId);
        //List<Item> findByOrderIdList = itemJPARepository.findByOrderIdJoinFetchAll(orderId);

        //then
        assertThat(findByOrderIdList).hasSize(2);
        assertThat(findByOrderIdList.get(0).getId()).isEqualTo(1); //itemId
        assertThat(findByOrderIdList.get(0).getOrder().getId()).isEqualTo(1); //orderId
        assertThat(findByOrderIdList.get(0).getOrder().getUser().getId()).isEqualTo(1); //userId
        assertThat(findByOrderIdList.get(0).getOption().getId()).isEqualTo(1); //optionId
        assertThat(findByOrderIdList.get(0).getOption().getProduct().getId()).isEqualTo(1); //productId
        assertThat(findByOrderIdList.get(0).getPrice()).isEqualTo(10000);
    }
}