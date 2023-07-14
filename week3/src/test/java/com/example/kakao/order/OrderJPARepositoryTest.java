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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderJPARepository orderJPARepository;

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

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        cartJPARepository.save(newCart(user, optionListPS.get(0), 5));
        cartJPARepository.save(newCart(user, optionListPS.get(1), 5));
        cartJPARepository.save(newCart(user, optionListPS.get(5), 5));

        em.clear();
    }

    // 주문 저장 test
    @Test
    public void order_save_test() throws JsonProcessingException {

        // given
        User user = userJPARepository.findById(1).orElseThrow();
        Order order = newOrder(user);

        // when
        Order saveOrder = orderJPARepository.save(order);

        // then
        assertThat(saveOrder.getId()).isEqualTo(1);
        assertThat(orderJPARepository.count()).isEqualTo(1);
    }

    // 주문 id로 주문 조회 test
    @Test
    public void order_findById_test() throws JsonProcessingException {

        // given
        int id = 1;
        User user = userJPARepository.findById(1).orElseThrow();
        orderJPARepository.save(newOrder(user));

        // when
        Order findOrder = orderJPARepository.findById(id);

        // then
        assertThat(findOrder.getId()).isEqualTo(id);
    }

    @Test
    public void order_findByUserId_test() throws JsonProcessingException {

        // given
        int id = 1;
        User user = userJPARepository.findById(1).orElseThrow();
        orderJPARepository.save(newOrder(user));

        // when
        List<Order> orderList = orderJPARepository.findByUserId(id);

        // then
        assertThat(orderList.get(0).getUser().getId()).isEqualTo(1);
    }

    // 주문된 아이템이 저장됐는지 test
    @Test
    public void item_save_test() throws JsonProcessingException {

        // give
        User user = userJPARepository.findById(1).orElseThrow();
        Cart cart = cartJPARepository.findById(1);
        Order order = orderJPARepository.save(newOrder(user));
        Item item = newItem(cart, order);

        // when
        Item saveItem = itemJPARepository.save(item);

        // then
        assertThat(saveItem.getOrder().getId()).isEqualTo(1);
        assertThat(saveItem.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    }

    // 주문된 아이템을 주문 번호로 조회 test
    @Test
    public void item_findByOrderId_test() throws JsonProcessingException {

        // give
        int id = 1;
        User user = userJPARepository.findById(1).orElseThrow();
        Cart cart = cartJPARepository.findById(1);
        Order order = orderJPARepository.save(newOrder(user));
        itemJPARepository.save(newItem(cart, order));

        // when
        List<Item> itemList = itemJPARepository.findByOrderId(id);

        // then
        assertThat(itemList.get(0).getOrder().getId()).isEqualTo(1);
    }
}
