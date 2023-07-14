package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.ItemJPARepository;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(ObjectMapper.class)
@DataJpaTest
public class OrderJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE order_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User userPS = userJPARepository.save(newUser("ssar"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartList = Arrays.asList(
                newCart(userPS, optionListPS.get(0), 5),
                newCart(userPS, optionListPS.get(1), 5)
        );
        orderJPARepository.saveAll(Arrays.asList(newOrder(userPS)));
        itemJPARepository.saveAll(
                cartList.stream()
                        .map(cart -> newItem(cart, newOrder(userPS)))
                        .collect(Collectors.toList())
        );

        em.clear();
    }

    @Test
    @DisplayName("(기능 12) 결제")
    public void order_save_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Order> orderListPS = orderJPARepository.mFindByUserId(id);

        String responseBody = om.writeValueAsString(orderListPS); // 직렬화하여 출력
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", orderListPS.get(0).getUser().getPassword()));
        Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(orderListPS.get(0).getUser().getRoles()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("(기능 13) 주문 결과 확인")
    public void order_findAll_test() throws JsonProcessingException {
        // given

        // when
        List<Order> orderListPS = orderJPARepository.mFindAll();
        String responseBody = om.writeValueAsString(orderListPS);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(orderListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(orderListPS.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
        assertTrue(BCrypt.checkpw("meta1234!", orderListPS.get(0).getUser().getPassword()));
        Assertions.assertThat(orderListPS.get(0).getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(orderListPS.get(0).getUser().getRoles()).isEqualTo("ROLE_USER");
    }
}
