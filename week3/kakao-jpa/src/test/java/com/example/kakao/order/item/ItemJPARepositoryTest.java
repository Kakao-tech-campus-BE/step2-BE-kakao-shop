package com.example.kakao.order.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;


@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD) // 클래스의 모든 테스트 케이스마다 시작하기 이전에 context 재생성
class ItemJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;


    @BeforeEach
    @DisplayName("더미 OrderItem 준비")
    public void setUp(){
        String username = "heechan";
        User user = newUser(username);
        userJPARepository.save(user);


        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Product> products = productDummyList();
        productJPARepository.saveAll(products);

        List<Option> options = optionDummyList(products);
        optionJPARepository.saveAll(options);

        Cart cart1 = newCart(user, options.get(0), 1);
        Cart cart2 = newCart(user, options.get(2), 1);
        cartJPARepository.saveAll(List.of(cart1, cart2));

        Item item1 = newItem(cart1, order);
        Item item2 = newItem(cart2, order);
        itemJPARepository.saveAll(List.of(item1, item2));

        em.clear();
    }


    @DisplayName("주문 아이템 생성")
    @Test
    void orderItem_create_test() {

        // given
        String username = "heechan2";
        User user = newUser(username);
        userJPARepository.save(user);

        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Product> products = productDummyList();
        productJPARepository.saveAll(products);

        List<Option> options = optionDummyList(products);
        optionJPARepository.saveAll(options);

        Cart cart1 = newCart(user, options.get(0), 1);
        Cart cart2 = newCart(user, options.get(2), 1);
        cartJPARepository.saveAll(List.of(cart1, cart2));

        Item item1 = newItem(cart1, order);
        Item item2 = newItem(cart2, order);

        int previousItemSize = itemJPARepository.findAll().size();


        // when
        itemJPARepository.saveAll(List.of(item1, item2));


        // then
        assertThat(itemJPARepository.findAll()).hasSize(previousItemSize + 2);
    }


    @DisplayName("pk로 주문 아이템 조회")
    @Test
    void orderItem_selectById_test() {

        // given
        int id = 1;

        // when
        Item item = itemJPARepository.findById(id).orElseThrow();

        // then
        assertThat(item).isNotNull();
        assertThat(item.getId()).isEqualTo(id);
    }


    @DisplayName("주문번호로 주문 아이템 조회")
    @Test
    void orderItem_selectByOrderId_test() {

        // given
        int orderId = 1;


        // when
        List<Item> items = itemJPARepository.findAllByOrderId(orderId);


        // then
        assertThat(items).isNotNull().hasSize(2);
        assertThat(items.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        assertThat(items.get(0).getOption().getPrice()).isEqualTo(10000);
        assertThat(items.get(0).getQuantity()).isEqualTo(1);
        assertThat(items.get(0).getOrder().getUser().getUsername()).isEqualTo("heechan");
        assertThat(items.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        assertThat(items.get(1).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        assertThat(items.get(1).getOption().getPrice()).isEqualTo(9900);
        assertThat(items.get(1).getQuantity()).isEqualTo(1);
        assertThat(items.get(1).getOrder().getUser().getUsername()).isEqualTo("heechan");
        assertThat(items.get(1).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

    }


    @DisplayName("없는 주문번호 조회")
    @Test
    void orderItem_selectByOrderId_failed_test() {

        // given
        int orderId = 10000;


        // when
        List<Item> items = itemJPARepository.findAllByOrderId(orderId);


        // then
        assertThat(items).isEmpty();
    }

}