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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("user_setup"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(user, optionListPS));

        em.clear();
    }

    @Test
    public void cart_save_test() {
        // given
        User user = newUser("회원-장바구니저장테스트");
        Product product = newProduct("상품-장바구니저장테스트", 16, 1000);
        Option option = newOption(product, "옵션-장바구니저장테스트", 1000);
        Cart cart = newCart(user, option, 2);

        // when
        Cart cartPS = cartJPARepository.save(cart);

        // then
        Assertions.assertThat(cartPS.getId()).isEqualTo(3);
        Assertions.assertThat(cartPS.getUser().getUsername()).isEqualTo("회원-장바구니저장테스트");
        Assertions.assertThat(cartPS.getOption().getOptionName()).isEqualTo("옵션-장바구니저장테스트");
        Assertions.assertThat(cartPS.getQuantity()).isEqualTo(2);
    }


    @Test
    public void cart_update_test() {
        // given
        int id = 1;
        int quantity = 3;

        // when
        cartJPARepository.updateQuantityById(id, quantity);

//        em.flush();
//        em.clear();

        Cart cart = cartJPARepository.findById(1);

        // then
        Assertions.assertThat(cart.getId()).isEqualTo(id);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(quantity);
    }


    @Test
    public void cart_findByUserId_test() throws JsonProcessingException {
        // given
        int userId = 1;

        // when
        List<Cart> carts = cartJPARepository.findByUserId(userId);
        String responseBody = om.writeValueAsString(carts);

        // then
        Assertions.assertThat(carts.size()).isEqualTo(2);

        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(0).getUser().getUsername()).isEqualTo("user_setup");
        Assertions.assertThat(carts.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(10000*5);

        Assertions.assertThat(carts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(1).getUser().getUsername()).isEqualTo("user_setup");
        Assertions.assertThat(carts.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(carts.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(1).getPrice()).isEqualTo(10900*5);

    }

}
