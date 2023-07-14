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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ObjectMapper.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private CartJPARepository cartJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @BeforeEach
    public void setUp(){

        User ssar = userJPARepository.save(newUser("ssar"));
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        List<Option> optionList = optionJPARepository.saveAll(optionDummyList(productList));
        cartJPARepository.saveAll(cartDummyList(ssar, optionList));

        ssar = userJPARepository.save(newUser("cos"));
        productList = productJPARepository.saveAll(productDummyList());
        optionList = optionJPARepository.saveAll(optionDummyList(productList));
        cartJPARepository.saveAll(cartDummyList(ssar, optionList));
        em.clear();
    }

    @DisplayName("카트 추가")
    @Test
    public void cart_add_test() throws JsonProcessingException {
        // given
        User bory = newUser("bory");
        Product product = newProduct("새로운 상품", 8, 20000);

        // when
        System.out.println("=====카트 추가=====");
        User user = userJPARepository.save(bory);
        Product productPS = productJPARepository.save(product);
        Option optionPS = optionJPARepository.save(newOption(productPS, "새로운 옵션", 3000));
        Cart cart = cartJPARepository.save(newCart(user, optionPS, 3));

        String responseBody = om.writeValueAsString(cart);
        System.out.println("추가 후: " + cart.getId());
        System.out.println(responseBody);

        // then
        assertEquals(cart.getId(), 11);
        assertEquals(cart.getOption().getId(), 97);
        assertEquals(cart.getOption().getOptionName(), "새로운 옵션");
        assertEquals(cart.getOption().getPrice(), 3000);
        assertEquals(cart.getOption().getProduct(), new Product(31, "새로운 상품", "", "/images/8.jpg", 20000));
        assertEquals(cart.getUser(), new User(3, "bory@nate.com", "meta1234!", "bory", "ROLE_USER"));
        assertEquals(cart.getQuantity(), 3);
        assertEquals(cart.getPrice(), 9000);
    }

    @DisplayName("사용자별 카트 조회")
    @Test
    public void cart_findByUserId_test() throws JsonProcessingException {
        // given
        int userId = 1;

        // when
        List<Cart> cartList = cartJPARepository.findByUserId(userId);

        System.out.println("카트 유저별 카트 조회========================");
        String responseBody = om.writeValueAsString(cartList);
        System.out.println("결과값 : " +responseBody);

        // then
        Cart cart = cartList.get(0);
        Assertions.assertThat(cart.getUser()).isEqualTo(new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
        Assertions.assertThat(cart.getId()).isEqualTo(1);
        Assertions.assertThat(cart.getOption().getId()).isEqualTo(1);
    }
    @DisplayName("카트 id별 카트 조회")
    @Test
    public void cart_findByCartId_test() throws JsonProcessingException {
        // given
        int cartId = 1;

        // when
        Optional<Cart> cart = cartJPARepository.findByCartId(cartId); // lazy
        if (cart.isPresent()){
            System.out.println("카트 id별 카트 조회========================");
            Cart mycart = cart.get();
            String responseBody = om.writeValueAsString(mycart);
            System.out.println("결과값 : "+responseBody);

            // then
            Assertions.assertThat(mycart.getUser()).isEqualTo(new User(1, "ssar@nate.com", "meta1234!", "ssar", "ROLE_USER"));
            Assertions.assertThat(mycart.getId()).isEqualTo(1);
            Assertions.assertThat(mycart.getQuantity()).isEqualTo(1);
        }
        else {
            System.out.println("카트를 찾을 수 없습니다");
        }
    }
    @DisplayName("카트 수정")
    @Test
    public void cart_update_test() throws JsonProcessingException {
        // given
        int cartId = 1;
        int quantity = 10;
        int price = 2000;

        // when
        Optional<Cart> cart = cartJPARepository.findByCartId(cartId); // lazy
        if (cart.isPresent()) {
            Cart cartPS = cart.get();
            String responseBody = om.writeValueAsString(cartPS);
            System.out.println("바뀌기 전 : "+responseBody);
            cartPS.update(quantity, price);

            responseBody = om.writeValueAsString(cartPS);
            System.out.println("바뀐 후 : "+responseBody);

            // then
            Assertions.assertThat(cartPS.getQuantity()).isEqualTo(10);
            Assertions.assertThat(cartPS.getPrice()).isEqualTo(2000);
            Assertions.assertThat(cartPS.getId()).isEqualTo(1);
        }
        em.flush();
    }
    @DisplayName("카트 삭제")
    @Test
    public void cart_delete_test(){
        // given
        int cartId = 1;

        // when
        Optional<Cart> cart = cartJPARepository.findByCartId(cartId); // lazy
        if (cart.isPresent()) {
            cartJPARepository.deleteById(cartId);
            em.flush();
        }
        else {
            System.out.println("카트를 삭제할 수 없습니다");
        }
        // then
    }
}