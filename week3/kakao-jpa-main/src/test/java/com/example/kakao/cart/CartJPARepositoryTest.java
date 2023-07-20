package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.OrderJPARepository;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
import java.util.ArrayList;
import java.util.List;



@DisplayName("장바구니 JPA Test")
@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    //@Autowired
    //private OrderJPARepository orderJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    //@Autowired
    //private ItemJPARepository itemJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("ssar"));
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));

        cartJPARepository.save(newCart(user, optionList.get(0),5));
        cartJPARepository.save(newCart(user, optionList.get(1),5));
/*
        List<Cart> cartList = new ArrayList<>();
        cartList.add(newCart(user, optionList.get(0),5));
        cartList.add(newCart(user, optionList.get(2),5));
        cartJPARepository.saveAll(cartList);

 */
        em.clear();


    }

    @Test
    public void AddCartTest(){
        //given
        int id = 1;
        int optionId = 3;


        User user = userJPARepository.findById(id).orElseThrow();
        Option option = optionJPARepository.findById(optionId).orElseThrow();
        newCart(userJPARepository.findById(id).orElseThrow(), optionJPARepository.findById(optionId).orElseThrow(),5);

        //when
        Cart cart = cartJPARepository.save(newCart(user, option, 5));

        //String responseBody = om.writeValueAsString(cartList);
        //System.out.println("테스트 : " + responseBody);

        //then
        Assertions.assertThat(cart.getQuantity()).isEqualTo(5);
        Assertions.assertThat(cart.getPrice()).isEqualTo(49500);
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        //Assertions.assertThat(cart.getOption().getId()).isEqualTo(optionId);
    }

    @Test
    public void findAllTest() throws JsonProcessingException{
        //given
        int id = 1;
        //User user = userJPARepository.findById(id).orElseThrow();

        //when
        List<Cart> cartList = cartJPARepository.mFindByUserId(id);
        //String responseBody = om.writeValueAsString(cartList);
        //System.out.println("테스트 : " + responseBody);

        //then
        Assertions.assertThat(cartList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(50000);
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");


    }

    @Test
    public void updateCartTest() throws JsonProcessingException{
        int id = 1;
        int quantity = 8;
        int price;

        Cart cart = cartJPARepository.findById(id).orElseThrow();

        price = cart.getOption().getPrice() * quantity;
        cart.update(quantity, price);

        List<Cart> cartList = cartJPARepository.mFindByUserId(id);
        em.flush();
        //String responseBody = om.writeValueAsString(cartList);
        //System.out.println("테스트 : " + responseBody);

        Assertions.assertThat(cart.getQuantity()).isEqualTo(8);
        Assertions.assertThat(cart.getPrice()).isEqualTo(80000);
        Assertions.assertThat(cart.getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cart.getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    }


}
