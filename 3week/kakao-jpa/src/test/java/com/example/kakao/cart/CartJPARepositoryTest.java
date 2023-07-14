package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        User user = userJPARepository.save(newUser("user"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        cartJPARepository.saveAll(Arrays.asList(
                newCart(user, options.get(0), 5),   // optionId: 1
                newCart(user, options.get(1), 5)    // optionId: 2
        ));

        em.clear();
    }

    @DisplayName("장바구니 담기")
    @Test
    public void cart_save_test() {
        User user2 = userJPARepository.save(newUser("user2"));
        Option option = optionJPARepository.findById(22).orElseThrow();
        Cart cart = cartJPARepository.save(newCart(user2, option, 4));

        Assertions.assertThat(cart.getId()).isEqualTo(3);
        Assertions.assertThat(cart.getUser().getId()).isEqualTo(2);
        Assertions.assertThat(cart.getOption().getId()).isEqualTo(22);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(4);
    }

    @DisplayName("장바구니 수정")
    @Test
    public void cart_update_test() {
        int id = 1;
        int quantity = 3;

        cartJPARepository.updateQuantityById(id, quantity);
        Cart cart = cartJPARepository.findById(id).orElseThrow();

        Assertions.assertThat(cart.getId()).isEqualTo(id);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("장바구니 조회")
    @Test
    public void cart_findByUserId_test() throws JsonProcessingException {
        int userId = 1;
        List<Cart> carts = cartJPARepository.findByUserId(userId);

        Assertions.assertThat(carts.size()).isEqualTo(2);

        Assertions.assertThat(carts.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(carts.get(0).getQuantity()).isEqualTo(5);

        Assertions.assertThat(carts.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(carts.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(carts.get(1).getQuantity()).isEqualTo(5);

    }

    @DisplayName("장바구니 전체 삭제")
    @Test
    public void cart_deleteAllByUserId_test() {
        int userId = 1;
        cartJPARepository.deleteByUserId(userId);

        List<Cart> carts = cartJPARepository.findByUserId(userId);
        Assertions.assertThat(carts.size()).isEqualTo(0);
    }

    @DisplayName("장바구니 개별 아이템 삭제")
    @Test
    public void cart_deleteById_test() {
        int id = 1;
        cartJPARepository.deleteById(id);

        Optional<Cart> cart = cartJPARepository.findById(id);
        Assertions.assertThat(cart.isPresent()).isFalse();


    }
}
