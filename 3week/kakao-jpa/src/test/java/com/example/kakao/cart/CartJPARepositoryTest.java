package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;

import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

  @Autowired
  private EntityManager em;

  @Autowired
  private CartJPARepository cartJPARepository;

  @Autowired
  private ProductJPARepository productJPARepository;

  @Autowired
  private UserJPARepository userJPARepository;

  @Autowired
  private OptionJPARepository optionJPARepository;


  @BeforeEach
  public void setUp() {

    User user = newUser("ssar");
    userJPARepository.save(user);

    List<Product> productListPS = productJPARepository.saveAll(productDummyList());

    List<Option> optionListOS = optionDummyList(productListPS);
    optionJPARepository.saveAll(optionListOS);
    Option option = optionJPARepository.findById(1).get();

    Cart cart = newCart(user, option, 5);
    cartJPARepository.save(cart);

    em.flush();
    em.clear();

  }

  @Test
  @DisplayName("장바구니 ID 조회 테스트")
  public void join_cart_number_test() throws Exception {
    //given
    int userId = 1;

    //when
    Cart joinCartNumber = cartJPARepository.findById(userId).orElse(null);
    Assertions.assertThat(joinCartNumber).isNotNull();

    //then
    Assertions.assertThat(joinCartNumber.getOption().getProduct().getId()).isEqualTo(1);
    Assertions.assertThat(joinCartNumber.getOption().getProduct().getProductName())
        .isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    Assertions.assertThat(joinCartNumber.getOption().getOptionName())
        .isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    Assertions.assertThat(joinCartNumber.getOption().getPrice()).isEqualTo(10000);
    Assertions.assertThat(joinCartNumber.getQuantity()).isEqualTo(5);
    Assertions.assertThat(joinCartNumber.getPrice()).isEqualTo(50000);
  }

  @Test
  @DisplayName("장바구니 수량 업데이트 테스트")
  public void update_cart_test() throws Exception {
    //given
    int cartId = 1;
    int newQuantity = 8;

    //when
    Cart cart = cartJPARepository.findById(cartId).orElse(null);
    Assertions.assertThat(cart).isNotNull();

    cart.update(newQuantity, cart.getOption().getPrice() * newQuantity);
    Cart updatedCart = cartJPARepository.save(cart);

    //then
    Assertions.assertThat(updatedCart.getQuantity()).isEqualTo(newQuantity);
    Assertions.assertThat(updatedCart.getPrice())
        .isEqualTo(cart.getOption().getPrice() * newQuantity);

  }

  @Test
  @DisplayName("장바구니 담기 테스트")
  public void add_cart_test() throws Exception {
    // given
    User user = newUser("ssar");
    Option option = optionJPARepository.findById(1).orElse(null);
    int quantity = 6;

    // when
    Cart cart = newCart(user, option, quantity);
    Cart savedCart = cartJPARepository.save(cart);

    // then
    Assertions.assertThat(savedCart.getId()).isNotNull();
    Assertions.assertThat(savedCart.getUser()).isEqualTo(user);
    Assertions.assertThat(savedCart.getOption()).isEqualTo(option);
    Assertions.assertThat(savedCart.getQuantity()).isEqualTo(quantity);
    Assertions.assertThat(savedCart.getPrice()).isEqualTo(option.getPrice() * quantity);

  }

}
