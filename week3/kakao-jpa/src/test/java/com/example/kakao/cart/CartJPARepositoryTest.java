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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
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
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        User user = newUser("ssar");
        userJPARepository.save(user);
        Option option1 = optionListPS.get(0);
        Option option2 = optionListPS.get(1);
        List<Cart> cartList = Arrays.asList(
                newCart(user, option1,5),
                newCart(user, option2,5));
        cartJPARepository.saveAll(cartList);
        em.clear();
    }

    @Test
    public void cart_test() throws JsonProcessingException {
        // given
        String email = "ssar@nate.com";

        //when
        List<Cart> cartTestList = cartJPARepository.mFindAll(email);
        //String responseBody = om.writeValueAsString(cartTestList);
        //System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(cartTestList.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
        Assertions.assertThat(cartTestList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartTestList.get(0).getQuantity()).isEqualTo(5);
    }

    @Test
    public void cart_add_test() throws JsonProcessingException {
        // given
        String email = "ssar@nate.com";

        //when
        Optional<User> userPS = userJPARepository.findByEmail(email);
        List<Option> optionPS = optionJPARepository.findAll();
        User user = userPS.orElseThrow(() -> new RuntimeException("User not found"));
        cartJPARepository.save(newCart(user, optionPS.get(2),3));
        List<Cart> cartTestList = cartJPARepository.mFindAll(email);
        String responseBody = om.writeValueAsString(cartTestList);
        System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(cartTestList.get(2).getUser().getEmail()).isEqualTo("ssar@nate.com");
        Assertions.assertThat(cartTestList.get(2).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        Assertions.assertThat(cartTestList.get(2).getQuantity()).isEqualTo(3);
    }

    @Test
    public void cart_update_test() throws JsonProcessingException {
        //given
        int cartId = 1; // 수정할 Option의 ID
        int quantity = 10; // 수정할 수량

        //when
        Cart cart = cartJPARepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));  // 수정할 Option 조회
        int price = cart.getOption().getPrice() * quantity;
        cart.update(quantity, price); // 카트 업데이트

        Cart cartPS = cartJPARepository.save(cart);
        CartResponse.UpdateDTO.CartDTO cartDTO = new CartResponse.UpdateDTO.CartDTO(cartPS);
        String responseBody = om.writeValueAsString(cartDTO);
        System.out.println("테스트 : "+responseBody);
        em.flush();

        //then
        Assertions.assertThat(cartPS.getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartPS.getPrice()).isEqualTo(100000);
    }
}
