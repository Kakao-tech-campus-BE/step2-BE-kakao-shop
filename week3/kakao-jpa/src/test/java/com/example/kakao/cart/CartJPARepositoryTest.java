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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {
    private CartJPARepository cartJPARepository;
    private OptionJPARepository optionJPARepository;
    private ProductJPARepository productJPARepository;
    private UserJPARepository userJPARepository;
    private EntityManager em;
    private ObjectMapper om;

    @Autowired
    public CartJPARepositoryTest(CartJPARepository cartJPARepository, OptionJPARepository optionJPARepository, ProductJPARepository productJPARepository, UserJPARepository userJPARepository, EntityManager em, ObjectMapper om) {
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
        this.userJPARepository = userJPARepository;
        this.em = em;
        this.om = om;
    }

    @BeforeEach
    public void setUp() {
        User user = userJPARepository.save(newUser("rhalstjr1999"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = Arrays.asList(
                newCart(user, options.get(0), 5),
                newCart(user, options.get(1), 5)
        );

        cartJPARepository.saveAll(carts);
        em.clear();
    }
    @AfterEach
    public void resetIndex() {
        cartJPARepository.deleteAll();
        userJPARepository.deleteAll();
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();

        em.clear();
    }

    @Test
    void 장바구니_담기_테스트(){
        //given
        User user = userJPARepository.save(newUser("gomen"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));

        //when
        List<Cart> carts = Arrays.asList(
                newCart(user, options.get(0), 5),
                newCart(user, options.get(1), 5)
        );

        cartJPARepository.saveAll(carts);

        //then
        cartJPARepository.findByUserId(user.getId());
    }

    @Test
    void 장바구니_조회_테스트() throws JsonProcessingException {
        //given
        int id = 1;

        //when
        List<Cart> carts = cartJPARepository.findByUserId(id).orElseGet(Collections::emptyList);

        System.out.println("============JSON 직렬화============");
        String responseBody = om.writeValueAsString(carts);
        System.out.println("테스트 : " + responseBody);

        //then

    }

    @Test
    void 장바구니_수정_테스트() {
        //given
        int id = 1;

        //then
        Cart cart = cartJPARepository.findById(id).orElseThrow(
                        () -> new RuntimeException("해당 장바구니가 존재하지 않습니다.")
        );

        cart.update(11, cart.getOption().getPrice() * 11);
        cartJPARepository.save(cart);

        Cart cart1 = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 장바구니가 존재하지 않습니다.")
        );

        //then
        Assertions.assertThat(cart1).isEqualTo(cart);
    }

    @Test
    void 장바구니_삭제_테스트() {
        //given
        int id = 1;

        //when
        Cart cart = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 장바구니가 존재하지 않습니다.")
        );
        cartJPARepository.delete(cart);
    }
}