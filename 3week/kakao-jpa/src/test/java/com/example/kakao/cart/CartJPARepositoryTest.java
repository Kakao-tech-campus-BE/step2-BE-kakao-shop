package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JacksonException;
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
public class CartJPARepositoryTest extends DummyEntity {
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
    private ObjectMapper om;
    @BeforeEach
    public void setUp() {
        em.clear(); //마지막에 em.clear()를 시키면 장바구니 수정하는부분에서 Dirty Checking 이 불가능하기 때문에 처음으로 두었다.
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User newUser = newUser("dlwlsgur1");
        userJPARepository.save(newUser);
        em.flush();
        User user = userJPARepository.findByEmail("dlwlsgur1@nate.com").get();
        cartJPARepository.saveAll(cartDummyList(user,optionListPS,5));
    }

    @Test // (userId가 1인) 장바구니 전체 조회
    public void cart_findByUserId() throws JsonProcessingException {
        //given
        int userId = 1;


        //when
        List<Cart> cartPS = cartJPARepository.findByUserId(userId).orElseThrow(
                () -> new RuntimeException("유저를 찾을 수 없습니다.")
        );

        String responseBody1 = om.writeValueAsString(cartPS);
        System.out.println("테스트 : "+responseBody1);

        //then
        Assertions.assertThat(cartPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartPS.get(1).getQuantity()).isEqualTo(5);
    }

    @Test // (cartId가 1인) 장바구니 수정
    public void cart_fingById() throws JacksonException {
        //given
        int cartId = 1;

        //when
        Cart cartPS2 = cartJPARepository.findById(cartId).orElseThrow(
                () -> new RuntimeException("장바구니를 찾을 수 없습니다")
        );

        String responseBody2 = om.writeValueAsString(cartPS2);
        System.out.println("테스트 : "+responseBody2);

        //then
        Assertions.assertThat(cartPS2.getId()).isEqualTo(1);
        Assertions.assertThat(cartPS2.getUser().getEmail()).isEqualTo("dlwlsgur1@nate.com");
        Assertions.assertThat(cartPS2.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    }

}
