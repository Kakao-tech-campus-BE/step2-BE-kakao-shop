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
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListOS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListCS = cartJPARepository.saveAll(cartDummyList(user, optionListOS));
        em.clear();
    }

    @Test
    public void carts_findAll_test() throws JsonProcessingException {
        // given
        List<Cart> carts = cartJPARepository.mFindAll();
        CartResponse.FindAllDTO response = new CartResponse.FindAllDTO(carts);

        // when
        System.out.println("DTO를 활용한 JSON 문자열 반환");
        String responseBody = om.writeValueAsString(response);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(50000);
        Assertions.assertThat(carts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(1).getPrice()).isEqualTo(54500);
    }
}
