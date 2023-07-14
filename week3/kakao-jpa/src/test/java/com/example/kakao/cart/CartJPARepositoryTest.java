package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cart Repository Test")
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    private final CartJPARepository cartJPARepository;
    private final EntityManager entityManager;
    private final UserJPARepository userJPARepository;
    private final OptionJPARepository optionJPARepository;

    public CartJPARepositoryTest(
            @Autowired CartJPARepository cartJPARepository,
            @Autowired EntityManager entityManager,
            @Autowired UserJPARepository userJPARepository,
            @Autowired OptionJPARepository optionJPARepository
    ) {
        this.cartJPARepository = cartJPARepository;
        this.entityManager = entityManager;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
    }

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("ssar"));
        List<Option> options = optionJPARepository.saveAll(optionDummyList(productDummyList()));
        List<Cart> carts = Arrays.asList(
                newCart(user, options.get(0), 5),
                newCart(user, options.get(2), 3),
                newCart(user, options.get(7), 2),
                newCart(user, options.get(13), 5),
                newCart(user, options.get(15), 1)
        );
        cartJPARepository.saveAll(carts);
        entityManager.clear();
    }

    @DisplayName("insert cart test")
    @Test
    public void insertTest() {
        // given
        User user = userJPARepository.findById(1).orElseThrow();
        Option option = optionJPARepository.findById(28).orElseThrow();
        Cart cart = newCart(user, option, 2);
        long previous_cart_count = cartJPARepository.count();

        // when
        Cart savedCart = cartJPARepository.save(cart);

        // then
        assertThat(savedCart)
                .extracting("option")
                .extracting("id")
                .isEqualTo(28);
        assertThat(savedCart)
                .extracting("quantity")
                .isEqualTo(2);
        assertThat(cartJPARepository.count()).isEqualTo(previous_cart_count + 1);
    }

    @DisplayName("find all cart using user-id")
    @Test
    public void findAllByUserIdTest() {
        // given

        // when
        List<Cart> cart = cartJPARepository.findAllByUserId(1);

        // then
        assertThat(cart).hasSize(5);
    }

    @DisplayName("update cart test")
    @Test
    public void updateTest() {
        // given
        Cart cart = cartJPARepository.findById(1);
        cart.update(50, 50 * 10000);

        // when
        Cart savedCart = cartJPARepository.save(cart);

        // then
        assertThat(savedCart)
                .extracting("quantity")
                .isEqualTo(cart.getQuantity());
        assertThat(savedCart)
                .extracting("price")
                .isEqualTo(cart.getPrice());
    }

    @DisplayName("delete cart test")
    @Test
    public void deleteTest() {
        // given
        long previous_count = cartJPARepository.count();

        // when
        cartJPARepository.deleteById(1);

        // then
        assertThat(cartJPARepository.count()).isEqualTo(previous_count - 1);
    }
}
