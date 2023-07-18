package com.example.kakao.Cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;


@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private EntityManager em;


    private User user=newUser("권해");
    private Option option;
    private Cart cart;

    @BeforeEach
    public void setUp(){
        userJPARepository.save(user);
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        option=optionJPARepository.findById(1).get();
        cart = newCart(user,option, 5);
        cartJPARepository.save(cart);
        em.clear();
    }


    @Test // 장바구니 조회 테스트 (User, Option은 lazy loading 사용)
    public void cart_findAll_test() throws JsonProcessingException {
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAll();


        //then
        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("권해");
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(50000);
    }

    @Test // 장바구니 Id로 장바구니 조회 (User,Option은 lazy loading 사용)
    public void cart_findById_test(){
        //given
        int id=1;
        //when
        Cart findCart=cartJPARepository.findById(id).get();
        //then
        Assertions.assertThat(findCart.getUser().getUsername()).isEqualTo("권해");
        Assertions.assertThat(findCart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(findCart.getPrice()).isEqualTo(50000);
    }
}
