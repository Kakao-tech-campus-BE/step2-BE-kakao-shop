package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.item.ItemJPARepository;
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

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {
    /*
        TODO :
            1. 카트 담기 테스트
            2. 카트 조회 테스트
            3. 카트 수정 테스트
            4. 카트 삭제 테스트
     */
    @Autowired
    private EntityManager em;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private CartJPARepository cartJPARepository;

    @BeforeEach
    public void setUp(){
        User userPS = userJPARepository.save(newUser("ikyeong"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.save(newCart(userPS, optionListPS.get(0),5));
        cartJPARepository.save(newCart(userPS, optionListPS.get(1),5));
        em.clear();
    }

    @Test
    public void cart_findAll_test() {
        //given
        int id = 1;

        //when
        List<Cart> cartListPS = cartJPARepository.findAllByUserId(id);

        //then
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(50000);
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartListPS.get(0).getUser().getUsername()).isEqualTo("ikyeong");
    }

    @Test
    public void cart_save_test(){
        //given
        User user = userJPARepository.findById(1).get();
        Option option = optionJPARepository.findById(5).get();
        newCart(user, option,5);
        //when
        Cart cart = cartJPARepository.save(newCart(user, option, 5));
        System.out.println("id : "+cart.getId());
        //then
    }

    @Test
    public void cart_update_test(){
        //given
        int id = 1;
        int quantity = 10;
        int price;
        //when
        Cart cartPS = cartJPARepository.findById(id).get();
        price = cartPS.getOption().getPrice() * quantity;
        cartPS.update(quantity, price);
        em.flush();
        //then

    }

    @Test
    public void cart_deleteAllByUserId_test(){
        //given
        int id = 1;
        //when
        cartJPARepository.deleteAllByUserId(id);
        List<Cart> cartListPS = cartJPARepository.findAllByUserId(id);
        //given
        Assertions.assertThat(cartListPS.size()).isEqualTo(0);
    }
}
