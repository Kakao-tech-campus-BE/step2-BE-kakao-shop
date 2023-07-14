package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
//private List<Cart> cartDummyList(List<Option> optionList){
//        return Arrays.asList(
//        newCart(optionList.get(0), 1, 5),
//        newCart(optionList.get(1), 2, 5)
//        );
//        }

@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {


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


    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();

        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User user = newUser("ssar");
        userJPARepository.save(user);

        List<Cart> testCartList = new ArrayList<>();
        testCartList.add(newCart(user,optionListPS.get(0),5));
        testCartList.add(newCart(user,optionListPS.get(1),5));

        cartJPARepository.saveAll(testCartList);
        em.clear();


    }



    @Test
    public void find_cart_by_userId_test(){
        // given
        int userId = 1;
        // when
        List<Cart> cartPSList = cartJPARepository.findByUserId(userId);
        //then

        Assertions.assertThat(cartPSList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartPSList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");

    }

    @Test
    public void add_cartItem_test(){
        // given

        cartJPARepository.findAll().forEach(System.out::println);
        int userId = 1;
        Cart testItem = newCart(userJPARepository.getReferenceById(userId),optionJPARepository.getReferenceById(3),3);
        cartJPARepository.save(testItem);

        // when
        cartJPARepository.findAll().forEach(System.out::println);;
        List<Cart> cartPSList = cartJPARepository.findByUserId(1);


        // then
        Assertions.assertThat(cartPSList.get(2).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        Assertions.assertThat(cartPSList.get(2).getUser().getEmail()).isEqualTo("ssar@nate.com");

    }

    @Test
    public void update_cartItem_Test(){
        // given

        int userId = 1;
        int optionId = 1;
        int quantity = 1;
        int price = 5000;

        // when
        Cart cart = cartJPARepository.findByUserIdAndOption_Id(1,1);
        cart.update(1,5000);
        cartJPARepository.findAll().forEach(System.out::println);

        // then
        Assertions.assertThat(cart.getQuantity()).isEqualTo(1);
        Assertions.assertThat(cart.getPrice()).isEqualTo(5000);

    }

    @Test
    public void delete_cartItem_by_Id_test(){
        // given
        int id = 1;
        // when
        List<Cart> carts = cartJPARepository.findByUserId(id);
        if(!carts.isEmpty()) {
            cartJPARepository.deleteByUserId(id);
        }

        // then
        Assertions.assertThat(cartJPARepository.findByUserId(1).size()).isEqualTo(0);

    }



}