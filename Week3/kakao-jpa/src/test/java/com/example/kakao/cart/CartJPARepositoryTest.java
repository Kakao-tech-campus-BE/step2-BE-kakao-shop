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
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        User ssar = userJPARepository.save(newUser("ssar"));
        User cos = userJPARepository.save(newUser("cos"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        List<Cart> cartDummyList = Arrays.asList(
                newCart(ssar, optionListPS.get(0), 5),
                newCart(ssar, optionListPS.get(1), 5),
                newCart(cos, optionListPS.get(2), 5),
                newCart(cos, optionListPS.get(3), 5)
        );
        cartJPARepository.saveAll(cartDummyList);
        em.clear();
    }

    @Test
    public void add_test() {
        // given
        Optional<User> userOP = userJPARepository.findById(1);
        Optional<Option> optionOP = optionJPARepository.findById(5);
        Cart cart = newCart(userOP.get(), optionOP.get(), 5);

        // when
        cartJPARepository.save(cart);

        // then
        Assertions.assertThat(cart.getId()).isEqualTo(5);
        Assertions.assertThat(cart.getUser()).isEqualTo(userOP.get());
        Assertions.assertThat(cart.getOption()).isEqualTo(optionOP.get());
        Assertions.assertThat(cart.getQuantity()).isEqualTo(5);
        Assertions.assertThat(cart.getPrice()).isEqualTo(optionOP.get().getPrice() * 5);
    }

    @Test
    public void cart_findByUserId_test() throws JsonProcessingException {
        // given
        Optional<User> userOP = userJPARepository.findById(2);

        // when
//        List<Cart> cartList = cartJPARepository.findAll();
        List<Cart> cartListPS = cartJPARepository.findByUserId(userOP.get().getId());

        // then
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(3);
        Assertions.assertThat(cartListPS.get(0).getUser()).isEqualTo(userOP.get());
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(49500);

        CartResponse.FindAllDTO findAllDTO = new CartResponse.FindAllDTO(cartListPS);
        String responsebody = om.writeValueAsString(findAllDTO);
        System.out.println("테스트 : " + responsebody);
    }

    @Test
    public void cart_update_test() throws JsonProcessingException {
        // given
        Optional<User> userOP = userJPARepository.findById(1);
        CartRequest.UpdateDTO updateDTO1 = new CartRequest.UpdateDTO();
        updateDTO1.setCartId(1);
        updateDTO1.setQuantity(10);
        CartRequest.UpdateDTO updateDTO2 = new CartRequest.UpdateDTO();
        updateDTO2.setCartId(2);
        updateDTO2.setQuantity(10);
        List<CartRequest.UpdateDTO> updateDTOList = Arrays.asList(
                updateDTO1,
                updateDTO2
        );

        // when
        List<Cart> cartListPS = cartJPARepository.findByUserId(userOP.get().getId());
        for (CartRequest.UpdateDTO updateDTO : updateDTOList) {
            for (Cart cart : cartListPS) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), updateDTO.getQuantity() * cart.getPrice());
                }
            }
        }
        em.flush();

        // then
        Assertions.assertThat(cartListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getUser()).isEqualTo(userOP.get());
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(10);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(500000);

        CartResponse.UpdateDTO updateDTO = new CartResponse.UpdateDTO(cartListPS);
        String responsebody = om.writeValueAsString(updateDTO);
        System.out.println("테스트 : " + responsebody);

    }

    @Test
    public void cart_deleteByUserId_test() {
        // given
        Optional<User> user = userJPARepository.findById(1);

        // when
        cartJPARepository.deleteByUserId(user.get().getId());
        em.flush();

        // then
        List<Cart> cartListPS = cartJPARepository.findByUserId(1);
        Assertions.assertThat(cartListPS).isEmpty();
    }
}
