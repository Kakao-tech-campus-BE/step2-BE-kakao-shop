package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ObjectMapper.class)
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    private User user;

    private List<Cart> carts;

    private Option option;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        user = newUser("보쨩");
        userJPARepository.save(user);

        option = optionDummyList(productDummyList()).get(0);
        optionJPARepository.save(option);

        carts = new ArrayList<>();

        em.clear();
    }
    @Test
    public void cart_findAll_test(){// User, Option -> LAZY 전략으로(지연 로딩만 사용)
        //given
        Cart cart = newCart(user, option, 5);
        carts.add(cart);
        cartJPARepository.saveAll(carts);

        //when
        List<Cart> cartList = cartJPARepository.findAll();

        //then
        assertThat(cartList.get(0).getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo(option.getOptionName());
        assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(option.getPrice());
        assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
    }
    @Test
    public void cart_update_test(){
        //given
        Option anotherOption = optionDummyList(productDummyList()).get(1);
        Cart cart1 = newCart(user, option, 5);
        Cart cart2 = newCart(user, anotherOption, 6);
        carts.add(cart1);
        List<Cart> beforeCartsList = cartJPARepository.saveAll(carts);

        //when
        carts.add(cart2);
        List<Cart> afterCartsList = cartJPARepository.saveAll(carts);

        //then
        assertThat(beforeCartsList.size()).isEqualTo(1);
        assertThat(afterCartsList.size()).isEqualTo(2);
    }
}
