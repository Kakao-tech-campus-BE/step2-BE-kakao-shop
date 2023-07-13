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

import java.util.Collections;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
class CartJPARepositoryTest extends DummyEntity {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ProductJPARepository productJPARepository;
    private final UserJPARepository userJPARepository;
    private final EntityManager em;
    private final ObjectMapper om;

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
        List<Cart> carts = cartDummys(user, options);

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
        List<Cart> carts = cartDummys(user, options);
        cartJPARepository.saveAll(carts);

        //then
        List<Cart> searchCarts = cartJPARepository.findByUserId(user.getId()).orElseThrow(
                () -> new RuntimeException("장바구니가 비어있습니다.")
        );

        Assertions.assertThat(carts).isEqualTo(searchCarts);
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
        int updateQuantity = 15;

        //then
        Cart cart = cartJPARepository.findById(id).orElseThrow(
                        () -> new RuntimeException("해당 장바구니가 존재하지 않습니다.")
        );

        cart.update(updateQuantity, cart.getOption().getPrice() * updateQuantity);
        cartJPARepository.save(cart);

        //then
        Cart searchCart = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 장바구니가 존재하지 않습니다.")
        );

        Assertions.assertThat(searchCart).isEqualTo(cart);
    }
}