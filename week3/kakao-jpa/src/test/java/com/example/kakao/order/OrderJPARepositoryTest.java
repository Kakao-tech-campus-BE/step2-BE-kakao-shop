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
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;


@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("phs"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = cartJPARepository.saveAll(cartDummyList(options, user));
        List<Order> orders = orderJPARepository.saveAll(orderDummyList(user));
        itemJPARepository.saveAll(itemDummyList(carts, orders));
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        em.clear();

    }

    @Test
    public void order_findByUserId_test() throws JsonProcessingException{
        // given
        int userId = 1;

        // when
//        User user = userJPARepository.findById(userId).get();
        List<Order> orders = orderJPARepository.findByUserId(userId);
        String responseBody = om.writeValueAsString(orders);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(orders.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(orders.get(0).getId()).isEqualTo(1);

    }

    @Test
    public void order_mFindByUserId_test() throws JsonProcessingException{

        // given
        int userId = 1;

        // when
        List<Order> orders = orderJPARepository.mFindByUserId(userId);
        String responseBody = om.writeValueAsString(orders);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(orders.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(orders.get(0).getId()).isEqualTo(1);


    }


    @Test
    public void item_findByOrderId_test() throws JsonProcessingException{

        // given
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.findByOrderId(orderId);
        String responseBody = om.writeValueAsString(items);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(items.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(items.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(items.get(0).getId()).isEqualTo(items.get(0).getOption().getId());

        Assertions.assertThat(items.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(items.get(0).getPrice()).isEqualTo(50000);


        Assertions.assertThat(items.get(1).getId()).isEqualTo(items.get(1).getOption().getId());
        Assertions.assertThat(items.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(items.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(items.get(1).getPrice()).isEqualTo(54500);
    }


    @Test
    public void item_mFindByOrderId_test() throws JsonProcessingException{
        // given
        int orderId = 1;

        // when
        List<Item> items = itemJPARepository.mFindByOrderId(orderId);
        String responseBody = om.writeValueAsString(items);
        System.out.println("테스트 : " + responseBody);

        // then
    }
}
