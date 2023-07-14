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

    @BeforeEach
    public void setUp(){
        User user = newUser("ssar");
        userJPARepository.save(user);
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        Option option = optionJPARepository.findById(1).get();

        //cartJPARepository.saveAll(cartDummyList(optionDummyList(productListPS)));
        //cartJPARepository.saveAll(cartDummyList2());
        cartJPARepository.save(newCart(user,option,2));

        em.clear();
    }

    @Test
    public void cart_findAll_test() throws JsonProcessingException {
        //given

        //when
        List<Cart> cartList = cartJPARepository.findAll();
        //String responseBody = om.writeValueAsString(cartList);
        //System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(cartList.get(0).getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    public void cart_findById_test() throws JsonProcessingException {
        //given
        int id = 1;
        //when
        Cart cart = cartJPARepository.findById(id).get();
        //String responseBody = om.writeValueAsString(cartList);
        //System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(cart.getUser().getUsername()).isEqualTo("ssar");
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cart.getQuantity()).isEqualTo(2);
    }



}
