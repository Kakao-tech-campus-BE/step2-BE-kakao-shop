package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("moly"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS =  optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(user, optionListPS));

        // 직렬화 오류 해결 왜지
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        em.clear();
    }

    @Test
    @DisplayName("유저의 장바구니 조회")
    public void cart_findByUserId_Test() throws JsonProcessingException {
        // given
        Integer userId = 1;

        // when
        List<Cart> cartList = cartJPARepository.findByUserId(userId);
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getUser().getEmail()).isEqualTo("moly@nate.com");
        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("moly");
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(10000);

        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getImage()).isEqualTo("/images/1.png");
        Assertions.assertThat(cartList.get(0).getOption().getProduct().getPrice()).isEqualTo(1000);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("장바구니 업데이트")
    public void cart_updateQuantity_Test() throws JsonProcessingException {
        // given
        Integer cartId = 1;
        Integer quantity = 100;

        // when
        Cart cart = cartJPARepository.findById(cartId).orElseThrow(
                () -> new RuntimeException("일치하는 장바구니 내역이 없습니다."));

        cart.update(quantity, cart.getPrice());
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cart.getId()).isEqualTo(1);
        Assertions.assertThat(cart.getUser().getEmail()).isEqualTo("moly@nate.com");
        Assertions.assertThat(cart.getUser().getUsername()).isEqualTo("moly");
        Assertions.assertThat(cart.getQuantity()).isEqualTo(100);
        Assertions.assertThat(cart.getPrice()).isEqualTo(10000);

        Assertions.assertThat(cart.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cart.getOption().getPrice()).isEqualTo(10000);
    }
}
