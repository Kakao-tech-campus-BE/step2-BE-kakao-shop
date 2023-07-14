package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private EntityManager em;

    @BeforeEach // 얘는 test 메서드가 실행 직전 마다 호출된다
    public void setup() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));

        User user = userJPARepository.save(newUser("ssar"));

        List<Cart> testCartList = Arrays.asList(newCart(user, optionListPS.get(0), 5), newCart(user, optionListPS.get(1), 5));
        cartJPARepository.saveAll(testCartList);
        em.clear();

    }
    @Test
    public void cart_test(){
        // given
        int userId = 1;
        // when
        List<Cart> cartPSList = cartJPARepository.findByUserId(userId);
        //then
        Assertions.assertThat(cartPSList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartPSList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");

    }

    @Test
    public void update_cart_test(){
        // given

        int userId = 1;
        int optionId = 1;

        // when
        Cart cart = cartJPARepository.findByUserIdAndOptionId(userId, optionId);
        cart.update(10,10000);
        System.out.println(cart);

        // then
        Assertions.assertThat(cart.getQuantity()).isEqualTo(10);
        Assertions.assertThat(cart.getPrice()).isEqualTo(10000);

    }

}

