package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
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
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.PostRemove;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("phs"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        cartJPARepository.saveAll(cartDummyList(options, user));
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        em.clear();
    }

    @Test
    public void findByUserId_test() throws JsonProcessingException {
        // given
        int userId = 1;


        // when
        List<Cart> carts = cartJPARepository.findByUserId(userId);
        String responseBody = om.writeValueAsString(carts);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(carts.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(50000);

        Assertions.assertThat(carts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(carts.get(1).getOption().getPrice()).isEqualTo(10900);
        Assertions.assertThat(carts.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(1).getPrice()).isEqualTo(54500);

    }

    @Test
    public void mFindByUserId() throws JsonProcessingException{
        // given
        int userId = 1;

        // when
        List<Cart> carts = cartJPARepository.mFindByUserId(userId);
        String responseBody = om.writeValueAsString(carts);
        System.out.println("테스트 : "+responseBody);


        // then
    }

    @Test
    public void cartUpdateQuantity_test() throws JsonProcessingException {
        // given
        int cartId = 1;
        int quantity = 10;

        // when
        cartJPARepository.updateQuantityById(cartId, quantity);

        Cart updatedCart = cartJPARepository.findById(cartId).get();

        String responseBody = om.writeValueAsString(updatedCart);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(updatedCart.getId()).isEqualTo(cartId);
        Assertions.assertThat(updatedCart.getQuantity()).isEqualTo(quantity);


    }


}
