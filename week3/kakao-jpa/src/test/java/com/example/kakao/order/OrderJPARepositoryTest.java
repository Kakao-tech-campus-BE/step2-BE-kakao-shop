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

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Cart> cartList = Arrays.asList(newCart(
                user, optionListPS.get(0), 5),
                newCart(user, optionListPS.get(1),5));
        cartJPARepository.saveAll(cartList);
        em.clear();
    }

    @Test
    public void order_save_test() throws JsonProcessingException {
        //given
        String email = "ssar@nate.com";

        //when
        User user = userJPARepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Cart> cartList = cartJPARepository.findAll();

        List<Item> itemList = Arrays.asList(
                newItem(cartList.get(0), order),
                newItem(cartList.get(1), order)
        );
        List<Item> itemListPS = itemJPARepository.saveAll(itemList);

        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);
    }

    @Test
    public void order_findById_test() throws JsonProcessingException {
        //given
        int orderId = 1;

        //when
        //아래 문장 추가하니 HibernateLAZYInitializer 오류 해결
        List<Option> optionListPS = optionJPARepository.findAll();
        User user = userJPARepository.findByEmail("ssar@nate.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = newOrder(user);
        orderJPARepository.save(order);

        List<Cart> cartList = cartJPARepository.findAll();

        List<Item> itemList = Arrays.asList(
                newItem(cartList.get(0), order),
                newItem(cartList.get(1), order)
        );
        itemJPARepository.saveAll(itemList);

        List<Item> itemListPS = itemJPARepository.mFindByOrderId(orderId);
        String responseBody = om.writeValueAsString(itemListPS);
        System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(itemListPS.get(0).getOrder().getId()).isEqualTo(1);
        Assertions.assertThat(itemListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(itemListPS.get(0).getQuantity()).isEqualTo(5);

    }

}
