package com.example.kakao.order;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
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
import org.assertj.core.api.Assertions;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(ObjectMapper.class)
@DataJpaTest
class OrderJPARepositoryTest extends DummyEntity {

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ItemJPARepository itemJPARepository;

    @Autowired
    EntityManager em;

    @Autowired
    ObjectMapper om;
    @Autowired
    private UserJPARepository userJPARepository;

    @BeforeEach
    public void setUp(){

        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE item_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();


        User user = userJPARepository.save(newUser("ssar"));

        Product product1 = productJPARepository.save(newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000));
        //1번 물품에 맞는 옵션들 가져오기
        Option option1 = optionJPARepository.save(newOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000));
        Option option2 = optionJPARepository.save(newOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 5종", 10900));
        Option option3 = optionJPARepository.save(newOption(product1,"01. 슬라이딩 지퍼백 크리스마스에디션 6종", 10100));

        //카트 더미데이터 가져오기
        Cart cart1 = cartJPARepository.save(newCart(user, option1, 5));
        Cart cart2 = cartJPARepository.save(newCart(user, option2, 5));
        Cart cart3 = cartJPARepository.save(newCart(user, option3, 5));

        Order order = orderJPARepository.save(newOrder(user));

        itemJPARepository.save(newItem(cart1,order));
        itemJPARepository.save(newItem(cart2,order));
        itemJPARepository.save(newItem(cart3,order));

        System.out.println("-------------------------------------");
        em.clear();

    }


    //주문 조회 확인하기
    @Test
    public void orderItem_test() throws JsonProcessingException {


    }

}