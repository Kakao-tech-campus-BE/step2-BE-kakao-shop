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

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cart Repository Test")
@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    private final CartJPARepository cartJPARepository;
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;
    private final UserJPARepository userJPARepository;
    private final OptionJPARepository optionJPARepository;

    public CartJPARepositoryTest(
            @Autowired CartJPARepository cartJPARepository,
            @Autowired EntityManager entityManager,
            @Autowired ObjectMapper objectMapper,
            @Autowired UserJPARepository userJPARepository,
            @Autowired OptionJPARepository optionJPARepository
    ) {
        this.cartJPARepository = cartJPARepository;
        this.objectMapper = objectMapper;
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
    public void insertTest() throws JsonProcessingException {
        // given
        User user = userJPARepository.findById(1).orElseThrow();
        Option option = optionJPARepository.findById(28).orElseThrow();
        Cart cart = newCart(user, option, 2);
        long previous_cart_count = cartJPARepository.count();

        // when
        Cart savedCart = cartJPARepository.save(cart);
        String response = objectMapper.writeValueAsString(savedCart);
        System.out.println("result set : " + response);

        // then
        assertThat(savedCart.getOption().getId()).isEqualTo(28);
        assertThat(savedCart.getQuantity()).isEqualTo(2);
        assertThat(cartJPARepository.count()).isEqualTo(previous_cart_count + 1);
    }

    @DisplayName("find all carts")
    @Test
    public void findAllTest() throws JsonProcessingException {
        // given

        // when
        List<Cart> cartItems = cartJPARepository.findAllByFetchJoin();
        String response = objectMapper.writeValueAsString(cartItems);
        System.out.println("result set : " + response);

        // then
        assertThat(cartItems).hasSize(5);
    }

    @DisplayName("find cart using cart-id")
    @Test
    public void findByIdTest() throws JsonProcessingException {
        // given

        // when
        Cart cart = cartJPARepository.findById(1);
        String response = objectMapper.writeValueAsString(cart);
        System.out.println("result set : " + response);

        // then
        assertThat(cart.getId()).isEqualTo(1);
        assertThat(cart.getUser().getUsername()).isEqualTo("ssar");
        assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        assertThat(cart.getQuantity()).isEqualTo(5);
    }

    @DisplayName("update cart test")
    @Test
    public void updateTest() {
        // given

        // when
        Cart cart = cartJPARepository.findById(1);
        cart.update(10, 10 * 10000);

        // then
        assertThat(cartJPARepository.findById(1).getQuantity()).isEqualTo(10);
        assertThat(cartJPARepository.findById(1).getPrice()).isEqualTo(10 * 10000);
    }

    @DisplayName("delete cart test")
    @Test
    public void deleteTest() {
        // given
        long previous_count = cartJPARepository.count();

        // when
        Cart cart = cartJPARepository.findById(1);
        cartJPARepository.delete(cart);

        // then
        assertThat(cartJPARepository.count()).isEqualTo(previous_count - 1);
    }
}
