package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        User ssar = userJPARepository.save(newUser("ssar"));
        cartJPARepository.save(newCart(ssar, optionJPARepository.findById(1), 5));
        cartJPARepository.save(newCart(ssar, optionJPARepository.findById(2), 5));
        em.clear();
    }

    @Test
    public void cart_add_test() throws JsonProcessingException{
        // given
        int id = 5;
        int quantity = 5;
        User abcd = userJPARepository.save(newUser("abcd"));

        //when
        Cart cart = cartJPARepository.save(newCart(abcd, optionJPARepository.findById(5), 5));
        CartDTO cartDTO = new CartDTO(cart);
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartDTO);
        System.out.println("테스트 : "+responseBody);

        // when
    }
    @Test
    public void cart_findAll_test() throws JsonProcessingException {
        // given

        // when
        List<Cart> cartListPS = cartJPARepository.findAll();
        List<CartDTO> cartDTOList = cartListPS.stream().map(cart -> {return new CartDTO(cart);}).collect(Collectors.toList());
        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartDTOList);
        System.out.println("테스트 : "+responseBody);

        // then
    }

    @Test
    public void cart_findByCartId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Optional<Cart> cartOP = cartJPARepository.findByCartIdFetchJoinUser(id); // Eager
        if(cartOP.isPresent()){
            Cart cartPS = cartOP.get();
            CartDTO cartDTO = new CartDTO(cartPS);
            System.out.println("json 직렬화 직전========================");
            String responseBody = om.writeValueAsString(cartDTO);
            System.out.println("테스트 : "+responseBody);
        }

        // then
    }

    @Test
    public void cart_update_test() throws JsonProcessingException{
        //given
        int cartId1 = 1;
        int quantity1 = 10;
        int cartId2 = 2;
        int quantity2 = 10;

        //when
        Optional<Cart> cartOP1 = cartJPARepository.findByCartIdJoinUser(cartId1);
        Optional<Cart> cartOP2 = cartJPARepository.findByCartIdJoinUser(cartId2);
        if(cartOP1.isPresent()){
            Cart cartPS1 = cartOP1.get();
            cartPS1.update(quantity1, cartPS1.getOption().getPrice() * quantity1);
        }
        if(cartOP2.isPresent()){
            Cart cartPS2 = cartOP2.get();
            cartPS2.update(quantity2, cartPS2.getOption().getPrice() * quantity2);
        }

        em.flush();
    }

}

