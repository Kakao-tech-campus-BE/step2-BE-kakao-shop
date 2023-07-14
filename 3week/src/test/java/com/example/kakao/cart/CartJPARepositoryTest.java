package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
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

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("ssar"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        cartJPARepository.save(newCart(user, optionListPS.get(0), 5));
        cartJPARepository.save(newCart(user, optionListPS.get(1), 5));

        em.clear();
    }

    @Test
    @DisplayName("(기능 8) 장바구니 담기")
    public void cart_add_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Cart> cartListPS = cartJPARepository.mFindByUserId(id);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : " + responseBody);

        // then
    }

    @Test
    @DisplayName("(기능 9) 장바구니 조회")
    public void cart_findAll_test() throws JsonProcessingException {
        // given

        // when
        List<Cart> cartListPS = cartJPARepository.mFindAll();
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(50000);
    }

    @Test
    @DisplayName("(기능 11) 주문")
    public void update_cart_test() throws JsonProcessingException {
        // given
        int id = 1;
        int quantity = 10;

        // when
        Optional<Cart> cartOP = cartJPARepository.mFindById(id);
        Cart cartPS = new Cart();

        if (cartOP.isPresent()) {
            cartPS = cartOP.get();
            cartPS.update(quantity, quantity * cartPS.getOption().getPrice());
            em.flush();

            String responseBody = om.writeValueAsString(cartPS); //직렬화하여 출력
            System.out.println("테스트 : " + responseBody);
        }

        // then
        Assertions.assertThat(cartPS.getId()).isEqualTo(1);
        Assertions.assertThat(cartPS.getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartPS.getPrice()).isEqualTo(100000);
    }
}
