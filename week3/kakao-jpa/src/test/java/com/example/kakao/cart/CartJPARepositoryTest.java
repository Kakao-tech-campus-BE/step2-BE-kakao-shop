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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.assertj.core.api.Assertions;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setup() {
        String userName = "ssar";
        User user = userJPARepository.save(newUser(userName));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.save(newCart(user,optionList.get(0),10));
        em.clear();
    }

    @Test
    public void save_cart_test() {
        //given
        String email = "ssar@nate.com";
        int optionId = 2;

        //when
        User user = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 회원이 없습니다")
        );
        Optional<Option> option = optionJPARepository.findById(optionId);
        Cart cart = newCart(user, option.get(), 10);
        cartJPARepository.save(cart);
        em.clear();

        //then
        Optional<Cart> findCart = cartJPARepository.findById(2);
        Assertions.assertThat(findCart.get().getUser().getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(findCart.get().getOption().getId()).isEqualTo(optionId);
        Assertions.assertThat(findCart.get().getQuantity()).isEqualTo(cart.getQuantity());

    }

    @Test
    public void find_cart_test() throws JsonProcessingException {
        //given
        int userId = 1;
        
        //when
        List<Cart> cartList = cartJPARepository.findCartByUserId(userId);
        for (Cart cart : cartList) {
            System.out.println("cart.getId() = " + cart.getId());
        }

        //then
        Assertions.assertThat(cartList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    public void update_cart_test() {
        //given
        int cartId = 1;

        //when
        Optional<Cart> cart = cartJPARepository.findById(cartId);
        cart.get().update(10, 40000);
        em.flush();
        Optional<Cart> updateCart = cartJPARepository.findById(cartId);

        //then
        Assertions.assertThat(updateCart.get().getPrice()).isEqualTo(40000);
        Assertions.assertThat(updateCart.get().getQuantity()).isEqualTo(10);

    }

}