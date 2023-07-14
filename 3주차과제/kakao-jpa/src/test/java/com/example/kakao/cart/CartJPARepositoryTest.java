package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User user = userJPARepository.save(newUser("user"));
        Product product = productJPARepository.save(newProduct("product1", 1, 10000));
        Option option = optionJPARepository.save(newOption(product,"optionName",1000));
        Option option2 = optionJPARepository.save(newOption(product,"optionName2",1001));
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        Cart cart2 = cartJPARepository.save(newCart(user, option2, 5));

        em.clear(); // 1차 캐시에서 걸리기 때문에 쿼리 테스트가 제대로 안되서 clear를 통해 쿼리가 가게끔 한다.
    }


    // 장바구니 조회
    @Test
    public void cart_findByUserId_test(){
        // given
        int userId = 1;

        // when
        List<Cart> carts = cartJPARepository.findByUserId(userId);

        // then
        Assertions.assertThat(cartJPARepository.count()).isEqualTo(2);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getOption().getOptionName()).isEqualTo("optionName");
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(carts.get(0).getPrice()).isEqualTo(1000*5);
        Assertions.assertThat(carts.get(1).getOption().getOptionName()).isEqualTo("optionName2");
    }


    @Test
    public void cart_deleteByUserId_test(){
        // given
        int userId = 1;

        // when
        cartJPARepository.deleteByUserId(userId);
        em.flush();
        // then
        assertTrue(cartJPARepository.findByUserId(1).isEmpty());
    }

    @Test
    public void cart_findById_test(){
        // given
        int cartId = 1;

        // when
        Cart cart = cartJPARepository.findById(1).get();

        // then
        Assertions.assertThat(cart.getId()).isEqualTo(1);
        Assertions.assertThat(cart.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(5);
        Assertions.assertThat(cart.getPrice()).isEqualTo(1000*5);
    }
}
