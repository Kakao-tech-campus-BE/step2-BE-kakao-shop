package com.example.kakao.cart;


import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartJPARepositoryTest extends DummyEntity {
  @Autowired
  private EntityManager em;

  @Autowired
  private CartJPARepository cartJPARepository;

  @Autowired
  private ProductJPARepository productJPARepository;

  @Autowired
  private OptionJPARepository optionJPARepository;

  @Autowired
  private UserJPARepository userJPARepository;

  @BeforeEach
  public void setUp(){

    resetAutoIncrement();

    User user = newUser("TestUser");
    userJPARepository.save(user);

    List<Product> productListPS = productJPARepository.saveAll(productDummyList());

    List<Option> options = optionDummyList(productListPS);
    optionJPARepository.saveAll(options);

    List<Cart> carts = cartDummyList(user, options);
    cartJPARepository.saveAll(carts);

    em.clear();

    System.out.println("------------ 테스트 셋업 ------------");
  }



  @Test
  @DisplayName("장바구니 확인")
  void testGetCart() {
    int userId = 1;

    List<Cart> carts = cartJPARepository.findAllByUserId(userId);

    // 화면에 필요한 객체: 상품, 옵션
    assertThat(carts).hasSize(48);
    assertThat(carts.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    assertThat(carts.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    assertThat(carts.get(carts.size()-1).getOption().getOptionName()).isEqualTo("블랙");
  }

  @Test
  @DisplayName("장바구니 아이템 수량 변경")
  void testUpdateCart() {
    Cart selectedCart = cartJPARepository.findById(1).get();

    // when
    int quantity = 5;
    cartJPARepository.updateQuantityById(selectedCart.getId(), quantity);

    em.detach(selectedCart);

    Cart updatedCart = cartJPARepository.findById(selectedCart.getId()).get();

    // then
    assertThat(updatedCart.getQuantity()).isEqualTo(quantity);
  }

  @Test
  @DisplayName("장바구니 아이템 1개 삭제")
  void testDeleteOneCart() {
    Optional<Cart> selectedCart = cartJPARepository.findById(1);

    selectedCart.ifPresent(cart -> cartJPARepository.deleteById(cart.getId()));

    List<Cart> carts = cartJPARepository.findAllByUserId(1);

    assertThat(carts).hasSize(47);
  }

  @Test
  @DisplayName("장바구니 아이템 전체 삭제")
  void testDeleteCart() {
    cartJPARepository.deleteAllByUserId(1);

    List<Cart> carts = cartJPARepository.findAllByUserId(1);

    assertThat(carts).isEmpty();
  }

  private void resetAutoIncrement() {
    em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
  }

}