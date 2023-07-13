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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    private final EntityManager em;

    private final CartJPARepository cartJPARepository;

    private final ObjectMapper om;

    private final UserJPARepository userJPARepository;

    private final OptionJPARepository optionJPARepository;

    private final ProductJPARepository productJPARepository;

    @Autowired
    public CartJPARepositoryTest (
            EntityManager em,
            CartJPARepository cartJPARepository,
            UserJPARepository userJPARepository,
            OptionJPARepository optionJPARepository,
            ObjectMapper om,
            ProductJPARepository productJPARepository) {
        this.em = em;
        this.cartJPARepository = cartJPARepository;
        this.userJPARepository = userJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.om = om;
        this.productJPARepository = productJPARepository;
    }

    @BeforeEach
    public void setUp() {
        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(ssar, optionListPS));
        em.clear();
    }

    @DisplayName("영속화 테스트")
    @Test
    public void join_test(){
        Cart cart = newCart(newUser("cos"), optionDummyList(productDummyList()).get(1), 1);
        System.out.println("영속화 되기 전 id : " + cart.getId());
        cartJPARepository.save(cart);
        System.out.println("영속화 된 후 id : " + cart.getId());
        assertEquals(3, cart.getId());
    }
}
