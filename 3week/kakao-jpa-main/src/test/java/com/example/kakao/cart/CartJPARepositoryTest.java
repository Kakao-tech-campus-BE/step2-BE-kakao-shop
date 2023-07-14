package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(ObjectMapper.class)
class CartJPARepositoryTest extends DummyEntity{

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    private User user;
    private Product product;
    private Option option;

    @BeforeEach
    public void setUp() {
        user = em.persistAndFlush(newUser("newUser"));
        product = em.persistAndFlush(newProduct("새로운 상품", 8, 20000));
        option = em.persistAndFlush(newOption(product, "새로운 옵션", 3000));
        em.clear();
    }

    @Test
    public void cart_add_test() {
        // given
        Cart cart = newCart(user, option, 3);

        // when
        Cart savedCart = cartJPARepository.saveAndFlush(cart);

        // then
        Assertions.assertThat(savedCart.getId()).isNotNull();
        Assertions.assertThat(savedCart.getOption().getOptionName()).isEqualTo("새로운 옵션");
        Assertions.assertThat(savedCart.getOption().getPrice()).isEqualTo(3000);
        Assertions.assertThat(savedCart.getUser().getUsername()).isEqualTo("newUser");
        Assertions.assertThat(savedCart.getQuantity()).isEqualTo(3);
        Assertions.assertThat(savedCart.getPrice()).isEqualTo(9000);
    }

    @Test
    public void cart_findByUserId_test() {
        // given
        Cart cart = em.persistAndFlush(newCart(user, option, 3));
        em.clear();

        // when
        List<Cart> carts = cartJPARepository.findByUserId(user.getId());

        // then
        Assertions.assertThat(carts).hasSize(1);
        Assertions.assertThat(carts.get(0).getId()).isEqualTo(cart.getId());
    }

    @Test
    public void cart_findByCartId_test() {
        // given
        Cart cart = em.persistAndFlush(newCart(user, option, 3));
        em.clear();

        // when
        Optional<Cart> foundCart = cartJPARepository.findById(cart.getId());

        // then
        Assertions.assertThat(foundCart).isPresent();
        Assertions.assertThat(foundCart.get().getId()).isEqualTo(cart.getId());
    }

    @Test
    public void cart_update_test() {
        // given
        Cart cart = em.persistAndFlush(newCart(user, option, 3));
        em.clear();

        // when
        Cart foundCart = cartJPARepository.findById(cart.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        foundCart.update(5, 15000);

        // then
        Assertions.assertThat(foundCart.getQuantity()).isEqualTo(5);
        Assertions.assertThat(foundCart.getPrice()).isEqualTo(15000);
    }

    @Test
    public void cart_delete_test() {
        // given
        Cart cart = em.persistAndFlush(newCart(user, option, 3));
        em.clear();

        // when
        cartJPARepository.deleteById(cart.getId());
        em.flush();
        Optional<Cart> foundCart = cartJPARepository.findById(cart.getId());

        // then
        Assertions.assertThat(foundCart).isEmpty();
    }
}
