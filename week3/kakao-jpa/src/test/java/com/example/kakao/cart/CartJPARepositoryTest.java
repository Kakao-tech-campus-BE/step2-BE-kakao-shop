package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionDummyList(productListPS);

        Cart carts = newCart(user, optionListPS.get(0), 5);
        cartJPARepository.save(carts);

        em.clear();

        System.out.println("setUp");
    }

    @Test // 장바구니 조회
    public void cart_findById() {
        // given
        int id = 1;

        // when
        List<Cart> cart_test = cartJPARepository.findById(id);

        // then
        assertThat(cart_test.get(0).getUser().getUsername()).isEqualTo("ssar");
        assertThat(cart_test.get(0).getOption().getId()).isEqualTo(1);
        assertThat(cart_test.get(0).getQuantity()).isEqualTo(5);
        assertThat(cart_test.get(0).getPrice()).isEqualTo(50000);

    } // rollback

    @Test // 장바구니 업데이트
    public void cart_update() {
        // given
        int id = 1;
        int quantity = 10;

        // when
        Cart updateCart = cartJPARepository.updateQuantity(id, quantity);

        // then
        assertThat(updateCart.getQuantity()).isEqualTo(quantity);
    }

}