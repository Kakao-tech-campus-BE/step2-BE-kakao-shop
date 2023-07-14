package com.example.kakao.item;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@Import(ObjectMapper.class)
public class ItemJPARepositoryTest extends DummyEntity {
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private OrderJPARepository orderJPARepository;

    @BeforeEach
    public void setUp() {
        em.clear();
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User newUser = newUser("dlwlsgur");
        userJPARepository.save(newUser);
        List<Cart> cart = cartJPARepository.saveAll(cartDummyList(newUser,optionListPS,5));
        Order order = newOrder(newUser);
        orderJPARepository.save(order);
        Item item = newItem(cart.get(0),order);
        itemJPARepository.save(item);

    }

    @Test
    public void item_findByOrderId() throws JacksonException {
        //given
        int orderId = 1;

        //when
        Item itemPS = itemJPARepository.findByOrderId(orderId).orElseThrow(
                () -> new RuntimeException("주문을 찾을 수 없습니다")
        );
        String responseBody1 = om.writeValueAsString(itemPS);
        System.out.println("테스트 : "+responseBody1);
        em.close();

        //then
        Assertions.assertThat(itemPS.getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemPS.getPrice()).isEqualTo(50000);
    }

    @Test
    public void item_findByOptionId() throws JacksonException {
        //given
        int optionId = 1;

        //when
        Item itemPS = itemJPARepository.findByOptionId(optionId).orElseThrow(
                () -> new RuntimeException("옵션을 찾을 수 없습니다")
        );
        String responseBody1 = om.writeValueAsString(itemPS);
        System.out.println("테스트 : "+responseBody1);
        em.close();

        //then
        Assertions.assertThat(itemPS.getPrice()).isEqualTo(50000);
        Assertions.assertThat(itemPS.getOption().getProduct().getPrice()).isEqualTo(1000);
    }
}
