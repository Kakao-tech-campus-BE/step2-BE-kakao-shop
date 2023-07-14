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
import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.assertj.core.api.Assertions.*;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ObjectMapper om;

    private User user;
    private Option option;
    private Cart cart;

    @BeforeEach
    public void setUp(){
        user = newUser("jiyoon");
        userJPARepository.save(user);

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        option = optionJPARepository.findById(1).get();

        cart = newCart(user,option, 5);
        cartJPARepository.save(cart);
        em.clear();
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    public void cart_findAll_test() throws JsonProcessingException {
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAll();

//        System.out.println("json 직렬화 직전========================");
//        String responseBody = om.writeValueAsString(cartList);
//        System.out.println("테스트 : "+responseBody);

        //then
        assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("jiyoon");
        assertThat(cartList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
        assertThat(cartList.get(0).getPrice()).isEqualTo(50000);
    }

    @Test
    @DisplayName("장바구니 id로 장바구니 조회")
    public void cart_findById_test(){
        //given
        int id=1;
        //when
        Cart findCart = cartJPARepository.findById(id).get();
        //then
        assertThat(findCart.getUser().getUsername()).isEqualTo("jiyoon");
        assertThat(findCart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        assertThat(findCart.getPrice()).isEqualTo(50000);
    }

//    @AfterEach
//    public void setDown() {
//        cartJPARepository.clearStore();
//    }

}
