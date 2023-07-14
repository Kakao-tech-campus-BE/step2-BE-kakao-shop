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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach //test 실행 전 호출 -> 백업
    public void setup() {
        //cart 만들기 -> 필요한 매개변수 user,option, quantity
        User userSP = userJPARepository.save(newUser("chaewon"));
        List<Product> productsSP = productJPARepository.saveAll(productDummyList());
        List<Option> optionsSP = optionJPARepository.saveAll(optionDummyList(productsSP));
        List<Cart> cartsSP = Arrays.asList(
                newCart(userSP, optionsSP.get(0), 1),
                newCart(userSP, optionsSP.get(1), 3),
                newCart(userSP, optionsSP.get(2), 5)
        );
        cartJPARepository.saveAll(cartsSP);

        em.clear();
        System.out.println("----TEST 시작----");
    }

    @DisplayName("Cart 생성 테스트")
    @Test
    public void cart_add_test() {
        //given
        User user = userJPARepository.save(newUser("won"));
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        List<Cart> carts = Arrays.asList(
                newCart(user, options.get(0), 7),
                newCart(user, options.get(1), 9),
                newCart(user, options.get(2), 11)
        );
        //when
        System.out.println("영속화");
        cartJPARepository.saveAll(carts);
        //then - 어떻게 확인을 해야할까...
//        Assertions.assertThat(cart.getId()).isEqualTo(2);
//        Assertions.assertThat(cart.getQuantity()).isEqualTo(3);
//        Assertions.assertThat(cart.getUser()).isEqualTo(user);
//        Assertions.assertThat(cart.getOption()).isEqualTo(options.get(1));
    }

    @DisplayName("Cart 조회 테스트")
    @Test
    public void cart_read_test() throws JsonProcessingException {
        //given
        int id=1;
        List<Product> products = productJPARepository.saveAll(productDummyList());
        List<Option> options = optionJPARepository.saveAll(optionDummyList(products));
        //when
        List<Cart> findCarts = cartJPARepository.findbyUserId(id)
                .orElseGet(Collections::emptyList);
        if (findCarts.isEmpty()) { Assertions.fail("빈 카트 입니다");}
        //then - setup의 카트 값과 같은지?
        //두개 카트 중 카트 1개만 진행
        Assertions.assertThat(findCarts.get(0).getOption().getOptionName()).isEqualTo(options.get(0).getOptionName());
        Assertions.assertThat(findCarts.get(0).getQuantity()).isEqualTo(1);
    }

    @DisplayName("Cart 업데이트 테스트")
    @Test
    public void cart_update_test() throws JsonProcessingException  {
        //given
        int id=1;
        List<Cart> findCarts = cartJPARepository.findbyUserId(id)
                .orElseGet(Collections::emptyList);
        if (findCarts.isEmpty()) { Assertions.fail("빈 카트 입니다");}

        Cart cart = findCarts.get(0);

        //when - 카트 수량 업데이트
        cart.update(20, cart.getPrice()*20);
       // cartJPARepository.save(cart);
        em.flush();

        //then
        List<Cart> findUpdatedCarts = cartJPARepository.findbyUserId(id)
                .orElseGet(Collections::emptyList);
        if (findUpdatedCarts.isEmpty()) { Assertions.fail("빈 카트 입니다");}

        Cart updatedCart = findUpdatedCarts.get(0);

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(findCarts);
        System.out.println("테스트 : "+getPrettyString(responseBody));

        Assertions.assertThat(cart.getQuantity()).isEqualTo(updatedCart.getQuantity());
        Assertions.assertThat(cart.getPrice()).isEqualTo(updatedCart.getPrice());
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
        System.out.println("----TEST 끝----");
    }
}
