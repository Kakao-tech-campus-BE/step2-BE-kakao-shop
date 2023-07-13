package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
  @Autowired private EntityManager em;
  @Autowired private OrderJPARepository orderJPARepository;
  @Autowired private UserJPARepository userJPARepository;
  @Autowired private ObjectMapper om;

  @BeforeEach
  public void setUp() {
    em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    User user = newUser("ssar");
    userJPARepository.save(user);
    orderJPARepository.save(newOrder(user));
    em.clear();
    System.out.println("=============");
  }

  @Test
  public void order_findAll_test() {
    // given
    int page = 0;
    int size = 9;

    // when
    List<Order> orders = orderJPARepository.findAll(PageRequest.of(page, size)).getContent();

    // then
    Assertions.assertThat(orders.size()).isEqualTo(1);
    Assertions.assertThat(orders.get(0).getId()).isEqualTo(1);
  }

  @Test
  public void order_findByUserId_test() {
    // given
    String email = "ssar@nate.com";

    // when
    User user =
        userJPARepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("해당 이메일을 찾을 수 없습니다."));
    List<Order> orders = orderJPARepository.findByUserId(1);

    // then
    Assertions.assertThat(orders.size()).isEqualTo(1);
    Assertions.assertThat(orders.get(0).getId()).isEqualTo(1);
    Assertions.assertThat(orders.get(0).getUser().getEmail()).isEqualTo(email);
  }
}
