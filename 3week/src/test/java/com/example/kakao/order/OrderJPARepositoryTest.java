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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {

  @Autowired
  private EntityManager em;

  @Autowired
  private OrderJPARepository orderJPARepository;

  @Autowired
  private ProductJPARepository productJPARepository;

  @Autowired
  private OptionJPARepository optionJPARepository;

  @Autowired
  private CartJPARepository cartJPARepository;

  @Autowired
  private ItemJPARepository itemJPARepository;

  @Autowired
  private UserJPARepository userJPARepository;

  @BeforeEach
  public void createDummyOrders(){
    resetAutoIncrement();

    User user = newUser("testUser");
    userJPARepository.save(user);

    List<Product> productListPS = productJPARepository.saveAll(productDummyList());

    List<Option> options = optionDummyList(productListPS);
    optionJPARepository.saveAll(options);

    List<Cart> carts = cartDummyList(user, options);

    Order order = newOrder(user);
    orderJPARepository.save(order);

    itemJPARepository.saveAll(itemDummyList(carts, order));

    em.clear();
    System.out.println("------------ 테스트 셋업 ------------");
  }


  @Test
  @DisplayName("주문 상세 확인")
  void testGetOrderDetail() {
    List<Item> items = itemJPARepository.findAllByOrderId(1);

    assertThat(items).hasSize(48);
    assertThat(items.get(0).getOrder().getId()).isEqualTo(1);
    assertThat(items.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
  }

  @Test
  @DisplayName("주문 내역 목록 확인")
  void testGetOrderList() {
    List<Order> orders = orderJPARepository.findAllByUserId(1);

    assertThat(orders).hasSize(1);
  }

  private void resetAutoIncrement() {
    em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
  }

}