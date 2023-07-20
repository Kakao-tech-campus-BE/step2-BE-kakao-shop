package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.user.UserJPARepositoryTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.auxiliary.MethodCallProxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.persistence.EntityManager;
import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    private final CartJPARepository cartJPARepository;
    private final EntityManager em;
    private final ObjectMapper om;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;


    @Autowired
    public CartJPARepositoryTest(CartJPARepository cartJPARepository, EntityManager em, ObjectMapper om, ProductJPARepository productJPARepository, OptionJPARepository optionJPARepository, UserJPARepository userJPARepository) {
        this.cartJPARepository = cartJPARepository;
        this.em = em;
        this.om = om;
        this.productJPARepository = productJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @BeforeEach
    public void setUp(){
        User user = userJPARepository.save(newUser("han"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = Arrays.asList(
                newCart(user, optionListPS.get(0), 5),
                newCart(user, optionListPS.get(1), 5),
                newCart(user, optionListPS.get(2), 10)
        );
        cartJPARepository.saveAll(cartListPS);
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
    @DisplayName("장바구니 생성")
    public void cart_createCart() throws JsonProcessingException{
        User user = userJPARepository.save(newUser("go"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartListPS = Arrays.asList(
                newCart(user, optionListPS.get(0), 5),
                newCart(user, optionListPS.get(1), 5),
                newCart(user, optionListPS.get(2), 10)
        );
        cartJPARepository.saveAll(cartListPS);

    }

    @Test
    @DisplayName("장바구니 조회")
    public void car_readCart() throws JsonProcessingException{
        //given
        int id = 1;
        //when
        List<Cart> cartListPS = cartJPARepository.readCartById(id);
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : " + responseBody);
        //then
        Assertions.assertThat(cartListPS.get(0).getUser().getUsername()).isEqualTo("han");
        Assertions.assertThat(cartListPS.get(0).getUser().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);

    }

    @Test
    @DisplayName("장바구니 수정")
    public void cart_updateCart() throws JsonProcessingException{
        int id = 1;
        int quantity = 10;
        Cart cart = cartJPARepository.findById(id).orElse(null);
        if (cart != null){
            cart.update(quantity,cart.getOption().getPrice() * quantity);
        }
        System.out.println(cart);
    }

    @Test
    @DisplayName("장바구니 삭제")
    public void cart_deleteCart() throws  JsonProcessingException{
        int id = 1;

        List<Cart> cartListPS = cartJPARepository.readCartById(id);
        cartJPARepository.deleteCartById(id);

    }
}