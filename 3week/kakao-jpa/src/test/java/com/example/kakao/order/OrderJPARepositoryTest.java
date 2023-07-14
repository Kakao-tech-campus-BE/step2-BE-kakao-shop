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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Transactional
public class OrderJPARepositoryTest extends DummyEntity {

  @Autowired
  private EntityManager em;

  @Autowired
  private OrderJPARepository orderJPARepository;

  @Autowired
  private ProductJPARepository productJPARepository;

  @Autowired
  private OptionJPARepository optionJPARepository;

  @Autowired
  private UserJPARepository userJPARepository;

  @Autowired
  private ItemJPARepository itemJPARepository;

  @BeforeEach
  public void setUp() {
    User user = newUser("ssar");
    userJPARepository.save(user);

    List<Product> productListPS = productJPARepository.saveAll(productDummyList());

    List<Option> optionOS = optionDummyList(productListPS);
    optionJPARepository.saveAll(optionOS);
    Option option = optionJPARepository.findById(1).get();

    Cart cart = newCart(user, option, 5);

    Order order = newOrder(user);
    orderJPARepository.save(order);

    Item item = newItem(cart, order);
    itemJPARepository.saveAll(Collections.singletonList(item));

    em.flush();
    em.clear();
  }

  @Test
  @DisplayName("orderID별 주문 확인")
  public void check_order_number_test() throws Exception {
    //given
    int orderId = 1;

    //when
    List<Item> checkOrderNumber = itemJPARepository.findByOrderId(orderId);
    Assertions.assertThat(checkOrderNumber).isNotNull();

    //then
    Assertions.assertThat(checkOrderNumber.get(0).getOrder().getId()).isEqualTo(1);
    Assertions.assertThat(checkOrderNumber.get(0).getOption().getProduct().getProductName())
        .isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    Assertions.assertThat(checkOrderNumber.get(0).getOption().getOptionName())
        .isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    Assertions.assertThat(checkOrderNumber.get(0).getOption().getPrice()).isEqualTo(10000);
    Assertions.assertThat(checkOrderNumber.get(0).getQuantity()).isEqualTo(5);
    Assertions.assertThat(checkOrderNumber.get(0).getPrice()).isEqualTo(50000);
  }

  @Test
  @DisplayName("주문 완료 확인")
  public void check_order_finish_test() throws Exception {
    //given
    int userId = 1;
    int orderId = 1;
    List<Order> checkUserNumber = orderJPARepository.findByUserId(userId);
    Assertions.assertThat(checkUserNumber).isNotNull();

    //when
    List<Item> checkOrderNumber = itemJPARepository.findByOrderId(orderId);

    //then
    Assertions.assertThat(checkOrderNumber.get(0).getId()).isEqualTo(userId);
  }
}
