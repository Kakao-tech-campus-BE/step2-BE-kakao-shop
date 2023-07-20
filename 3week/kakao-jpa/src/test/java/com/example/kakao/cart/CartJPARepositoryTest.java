package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
  @Autowired private EntityManager em;
  @Autowired private CartJPARepository cartJPARepository;
  @Autowired private OptionJPARepository optionJPARepository;
  @Autowired private ProductJPARepository productJPARepository;
  @Autowired private ObjectMapper om;
  @Autowired private UserJPARepository userJPARepository;

  @BeforeEach
  public void setUp() {
    em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    User user = userJPARepository.save(newUser("ssar"));
    Product product = productJPARepository.save(newProduct("치킨", 1, 16000));
    Option option = optionJPARepository.save(newOption(product, "후라이드", 16000));
    cartJPARepository.save(newCart(user, option, 2));
    em.clear();
    System.out.println("=============");
  }

  @Test
  public void cart_findByUserId_test() {
    // given
    int userId = 1;

    // when
    List<Cart> carts = cartJPARepository.findByUserId(userId);

    // then
    Assertions.assertThat(carts.size()).isEqualTo(1);

    Cart cart = carts.get(0);
    Assertions.assertThat(cart.getId()).isEqualTo(1);
    Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("후라이드");
    Assertions.assertThat(cart.getOption().getPrice()).isEqualTo(16000);
    Assertions.assertThat(cart.getQuantity()).isEqualTo(2);
  }
}
