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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
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
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User ssar = userJPARepository.save(newUser("ssar"));
//        User cos = userJPARepository.save(newUser("cos"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartDummyList = Arrays.asList(
                newCart(ssar, optionListPS.get(0), 5),
                newCart(ssar, optionListPS.get(1), 5)
//                newCart(cos, optionListPS.get(2), 5),
//                newCart(cos, optionListPS.get(3), 5)
        );
        cartJPARepository.saveAll(cartDummyList);
        Order order = orderJPARepository.save(newOrder(ssar));
        List<Item> itemDummyList = Arrays.asList(
                newItem(cartDummyList.get(0), order),
                newItem(cartDummyList.get(1), order)
        );
        itemJPARepository.saveAll(itemDummyList);
        em.clear();
    }

    @Test
    public void save_test() throws JsonProcessingException {
        // given
        Optional<User> user = userJPARepository.findById(1);
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartDummyList = Arrays.asList(
                newCart(user.get(), optionListPS.get(2), 5),
                newCart(user.get(), optionListPS.get(3), 5)
        );
        cartJPARepository.saveAll(cartDummyList);

        // when
        Order order = orderJPARepository.save(newOrder(user.get()));
        List<Item> itemDummyList = Arrays.asList(
                newItem(cartDummyList.get(0), order),
                newItem(cartDummyList.get(1), order)
        );
        itemJPARepository.saveAll(itemDummyList);

        // then
        Assertions.assertThat(itemDummyList.get(0).getId()).isEqualTo(3);
        Assertions.assertThat(itemDummyList.get(0).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        Assertions.assertThat(itemDummyList.get(0).getOrder().getId()).isEqualTo(2);
        Assertions.assertThat(itemDummyList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemDummyList.get(0).getPrice()).isEqualTo(49500);

        OrderResponse.FindByIdDTO findByIdDTO = new OrderResponse.FindByIdDTO(order, itemDummyList);
        String responsebody = om.writeValueAsString(findByIdDTO);
        System.out.println("테스트 : " + responsebody);
    }

    @Test
    public void order_findByOrderId_test() throws JsonProcessingException {
        // given
//        Optional<User> userOP = userJPARepository.findById(1);
        Optional<Order> orderOP = orderJPARepository.findById(1);

        // when
        List<Item> itemListPS = itemJPARepository.findByOrderId(orderOP.get().getId());

        // then
        Assertions.assertThat(itemListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(itemListPS.get(0).getPrice()).isEqualTo(50000);

        OrderResponse.FindByIdDTO findByIdDTO = new OrderResponse.FindByIdDTO(orderOP.get(), itemListPS);
        String responsebody = om.writeValueAsString(findByIdDTO);
        System.out.println("테스트 : " + responsebody);
    }
}
