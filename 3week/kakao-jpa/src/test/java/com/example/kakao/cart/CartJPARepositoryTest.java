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
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    private UserJPARepository userJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        User userPS = userJPARepository.save(newUser("cha"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        em.clear();
    }

    @Test
    public void cart_findAll_test() throws JsonProcessingException {
        // given

        // when
        List<Cart> cartList = cartJPARepository.findAll();

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartList.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartList.get(0).getPrice()).isEqualTo(100000);

    }

    @Test
    public void cart_findById_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Optional<Cart> cart = cartJPARepository.findById(id);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cart.get().getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cart.get().getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cart.get().getQuantity()).isEqualTo(10);
        Assertions.assertThat(cart.get().getPrice()).isEqualTo(100000);

    }


    @Test
    public void cart_findByUserId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Cart> cartList = cartJPARepository.mFindByUserId(1);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartList.get(1).getQuantity()).isEqualTo(20);
    }

    @Test
    public void cart_save_test() throws JsonProcessingException {
        // given
        User user = newUser("test");
        Product product = newProduct("쌀과자", 100, 10000);
        Option option = newOption(product, "꿀쌀과자", 100000);
        Cart cart = newCart(user, option, 10);

        // when
        userJPARepository.save(user);
        productJPARepository.save(product);
        optionJPARepository.save(option);
        Cart savedCart = cartJPARepository.save(cart);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(savedCart).isEqualTo(cart);
    }

    @Test
    public void cart_update_test() throws JsonProcessingException {
        // given
        int id = 1;
        int quantity = 99;


        // when
        Cart cart = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 카트를 조회할 수 없습니다.")
        );

        cart.update(quantity, cart.getOption().getPrice()*quantity);

        cartJPARepository.save(cart);
        em.flush();
        em.detach(cart);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : "+responseBody);

        cart = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 카트를 조회할 수 없습니다.")
        );

        // then
        Assertions.assertThat(cart.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void cart_mUpdate_test() throws JsonProcessingException {
        // given
        int id = 1;
        int quantity = 99;

        // when
        Cart cart = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 카트를 조회할 수 없습니다.")
        );

        cartJPARepository.updateCartById(id, quantity, cart.getOption().getPrice()*quantity);

        em.detach(cart);
        cart = cartJPARepository.findById(1).get();

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cart.getQuantity()).isEqualTo(quantity);
    }



    @Test
    public void cart_delete_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Optional<Cart> optionalCart = cartJPARepository.findById(id);

        if (optionalCart.isPresent()) {
            optionJPARepository.delete(optionalCart.get().getOption());
            cartJPARepository.deleteById(optionalCart.get().getId());
        }

        List<Cart> cartList = cartJPARepository.findAllByUserId(1);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartList);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartList.size()).isEqualTo(8);
    }

}
