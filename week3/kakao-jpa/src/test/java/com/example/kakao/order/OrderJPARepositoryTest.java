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
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));
        List<Cart> cartList = cartJPARepository.saveAll(cartDummyList(user, optionList));
        Order order = orderJPARepository.save(newOrder(user));
        List<Item> itemList = itemJPARepository.saveAll(itemDummyList(cartList, order));
        em.clear();
    }

    @Test
    public void show_user_order() throws JsonProcessingException {
        // given
        Order order = orderJPARepository.mFindOrder();
        List<Item> itemList = itemJPARepository.mFindAll();
        OrderResponse.FindByIdDTO response = new OrderResponse.FindByIdDTO(order, itemList);

        // when
        System.out.println("DTO를 활용한 JSON 문자열 반환 - OrderDTO");
        String responseBody = om.writeValueAsString(response);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(order.getId()).isEqualTo(1);
        Assertions.assertThat(order.getUser().getUsername()).isEqualTo("ssar");
    }
}
