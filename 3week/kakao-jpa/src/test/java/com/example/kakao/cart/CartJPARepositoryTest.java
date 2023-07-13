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
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User user = newUser("sonny");
        Product product = newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000);
        Option option1 = newOption(product, "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000);
        Option option2 = newOption(product, "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900);

        // todo Product에 cascadeType.Persist 속성을 넣는 것 vs Product와 option의 연관관계를 끊는 것 어떤 것이 더 좋을까?
        userJPARepository.save(user);
        productJPARepository.save(product);
        optionJPARepository.save(option1);
        optionJPARepository.save(option2);
         // jpql 실행 시 flush 자동 호출

        em.clear();// 준영속 상태로 만듬 -> 쿼리 보기 위해서
    }

    @Test
    public void cart_saveAll_test() throws JsonProcessingException {

        //given
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(5);

        User user = userJPARepository.findByEmail("sonny@nate.com").orElseThrow(
                () -> new RuntimeException("해당 회원은 존재하지 않습니다.")
        );

        //when

        Option option1 = optionJPARepository.findById(saveDTO1.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager


        Option option2 = optionJPARepository.findById(saveDTO2.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager

        Cart cart1 = newCart(user, option1, saveDTO1.getQuantity());
        Cart cart2 = newCart(user, option2, saveDTO2.getQuantity());
        List<Cart> carts = List.of(cart1, cart2);
        List<Cart> savedCarts = cartJPARepository.saveAll(carts);
        String responseBody = om.writeValueAsString(savedCarts);
        System.out.println(responseBody);

        //then
        Assertions.assertThat(savedCarts.get(0).getOption().getId()).isEqualTo(saveDTO1.getOptionId());
        Assertions.assertThat(savedCarts.get(0).getQuantity()).isEqualTo(saveDTO1.getQuantity());
        Assertions.assertThat(savedCarts.get(1).getOption().getId()).isEqualTo(saveDTO2.getOptionId());
        Assertions.assertThat(savedCarts.get(1).getQuantity()).isEqualTo(saveDTO2.getQuantity());

    }

    @Test
    public void cart_findAllWithOptionsUsingJoin_test() throws JsonProcessingException {

        // given
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(5);

        User user = userJPARepository.findByEmail("sonny@nate.com").orElseThrow(
                () -> new RuntimeException("해당 회원은 존재하지 않습니다.")
        );

        //when

        Option option1 = optionJPARepository.findById(saveDTO1.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager


        Option option2 = optionJPARepository.findById(saveDTO2.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager

        Cart cart1 = newCart(user, option1, saveDTO1.getQuantity());
        Cart cart2 = newCart(user, option2, saveDTO2.getQuantity());
        List<Cart> carts = List.of(cart1, cart2);
        cartJPARepository.saveAll(carts);

        em.clear();

        // when
        List<Cart> cartList = cartJPARepository.findAllWithOptionsUsingJoinByEmail("sonny@nate.com");

        cartList.forEach(cart ->{
            Option option = cart.getOption();
            System.out.println(option);
            Product product = option.getProduct();
            System.out.println(product);
        });

        // then
        Assertions.assertThat(cartList.size()).isEqualTo(2);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartList.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(cartList.get(1).getOption().getPrice()).isEqualTo(10900);
    }

    @Test
    public void cart_findAllWithOptionsUsingFetchJoin_test() throws JsonProcessingException {

        // given
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(5);

        User user = userJPARepository.findByEmail("sonny@nate.com").orElseThrow(
                () -> new RuntimeException("해당 회원은 존재하지 않습니다.")
        );

        Option option1 = optionJPARepository.findById(saveDTO1.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager


        Option option2 = optionJPARepository.findById(saveDTO2.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager

        Cart cart1 = newCart(user, option1, saveDTO1.getQuantity());
        Cart cart2 = newCart(user, option2, saveDTO2.getQuantity());
        List<Cart> carts = List.of(cart1, cart2);
        cartJPARepository.saveAll(carts);

        em.clear();

        // when
        List<Cart> cartList = cartJPARepository.findAllWithOptionsUsingFetchJoinByEmail("sonny@nate.com");

        cartList.forEach(cart ->{
            Option option = cart.getOption();
            System.out.println(option);
            Product product = option.getProduct();
            System.out.println(product);
        });

        // then
        Assertions.assertThat(cartList.size()).isEqualTo(2);
        Assertions.assertThat(cartList.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartList.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartList.get(1).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(cartList.get(1).getOption().getPrice()).isEqualTo(10900);
    }

    @Test
    public void cart_update_test(){

        // given
        CartRequest.SaveDTO saveDTO1 = new CartRequest.SaveDTO();
        saveDTO1.setOptionId(1);
        saveDTO1.setQuantity(5);

        CartRequest.SaveDTO saveDTO2 = new CartRequest.SaveDTO();
        saveDTO2.setOptionId(2);
        saveDTO2.setQuantity(5);

        User user = userJPARepository.findByEmail("sonny@nate.com").orElseThrow(
                () -> new RuntimeException("해당 회원은 존재하지 않습니다.")
        );

        Option option1 = optionJPARepository.findById(saveDTO1.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager


        Option option2 = optionJPARepository.findById(saveDTO2.getOptionId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 옵션입니다.")
        ); // eager

        Cart cart1 = newCart(user, option1, saveDTO1.getQuantity());
        Cart cart2 = newCart(user, option2, saveDTO2.getQuantity());
        List<Cart> carts = List.of(cart1, cart2);
        cartJPARepository.saveAll(carts);

        em.clear(); // 현재 준영속상태

        //when

        CartRequest.UpdateDTO updateDTO1 = new CartRequest.UpdateDTO();
        updateDTO1.setCartId(1);
        updateDTO1.setQuantity(10);

        CartRequest.UpdateDTO updateDTO2 = new CartRequest.UpdateDTO();
        updateDTO2.setCartId(2);
        updateDTO2.setQuantity(10);

        Cart findCart1 = cartJPARepository.findCartWithOptionUsingFetchJoinById(updateDTO1.getCartId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 장바구니입니다.")
        );


        Cart findCart2 = cartJPARepository.findCartWithOptionUsingFetchJoinById(updateDTO2.getCartId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 장바구니입니다.")
        );

        findCart1.update(updateDTO1.getQuantity(),findCart1.getOption().getPrice()*updateDTO1.getQuantity());
        findCart2.update(updateDTO2.getQuantity(),findCart2.getOption().getPrice()*updateDTO2.getQuantity());

        //then

        Assertions.assertThat(findCart1.getPrice()).isEqualTo(100000);
        Assertions.assertThat(findCart2.getPrice()).isEqualTo(109000);
    }



}
