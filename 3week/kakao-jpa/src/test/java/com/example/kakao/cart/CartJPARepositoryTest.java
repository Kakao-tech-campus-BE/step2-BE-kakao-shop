package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.Order;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {

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

    @BeforeEach
    public void setup() {
        User user = userJPARepository.save(newUser("ssar"));

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productListPS));

        Cart cart = cartJPARepository.save(newCart(user,optionList.get(4),10));

        em.clear();
    }

    @Test
    public void addCart_test() { // 6. 장바구니 담기 기능 테스트
        //given
        int optionId = 4;

        //when
        User user = newUser("ssar2");

        Optional<Option> option = optionJPARepository.findById(optionId);
        Cart cart = cartJPARepository.save(newCart(user, option.get(), 10));
        Optional<Cart> findCart = cartJPARepository.findById(2);

        //then
        Assertions.assertThat(findCart.get().getId()).isEqualTo(cart.getId());
        Assertions.assertThat(findCart.get().getUser().getUsername()).isEqualTo(cart.getUser().getUsername());
        Assertions.assertThat(findCart.get().getOption().getId()).isEqualTo(optionId);
        Assertions.assertThat(findCart.get().getQuantity()).isEqualTo(cart.getQuantity());
    }

    @Test
    public void cart_findByUserId_test() throws JsonProcessingException { // 7. 장바구니 조회 기능 테스트 by userID
        //given
        int userId = 1;

        //when
        List<Cart> cartPSList = cartJPARepository.findCartByUserId(userId);

        //then
        Assertions.assertThat(cartPSList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartPSList.get(0).getUser().getId()).isEqualTo(userId);
    }

    @Test
    public void cart_findByCartId_test() throws JsonProcessingException{ // by cartID
        //given
        int cartId = 1;

        //when
        Cart cart = cartJPARepository.findById(cartId).get();

        //then
        Assertions.assertThat(cart.getId()).isEqualTo(cartId);
    }

    @Test
    public void updateCart_test() throws JsonProcessingException { // 8. 장바구니 수정 기능 테스트
        //given
        int cartId = 1;

        //when
        Optional<Cart> cart = cartJPARepository.findById(cartId);
        cart.get().update(100, 100);
        em.flush();

        Optional<Cart> newCart = cartJPARepository.findById(cartId);

        //then
        Assertions.assertThat(newCart.get().getPrice()).isEqualTo(100);
        Assertions.assertThat(newCart.get().getQuantity()).isEqualTo(100);
    }
}