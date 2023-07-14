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
    private CartJPARepository cartJPARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserJPARepository userJPARepository;


    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionDummy = optionJPARepository.saveAll(optionDummyList(productListPS));
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.clear();

    }

    @Test
    public void cart_test() throws JsonProcessingException {
        // given
        int id = 1;
        int quantity = 5;
        int optionDummyId = 0;

        // when
        List<Option> optionDummy = optionJPARepository.mFindByProductId(id); // fetch join으로 N+1 문제 해결
        User user = userJPARepository.save(newUser("gijun"));
        Cart cart = cartJPARepository.save(newCart(user,optionDummy.get(optionDummyId),quantity));
        String responseBody = om.writeValueAsString(cart);
        System.out.println("테스트 : " + responseBody);


        // then
        Assertions.assertThat(cart.getId()).isEqualTo(1); // Cart의 pk를 확인합니다.
        Assertions.assertThat(cart.getUser().getUsername()).isEqualTo("gijun"); // username을 체크합니다.
        Assertions.assertThat(cart.getQuantity()).isEqualTo(5); // 수량이 맞는지 비교합니다.
        Assertions.assertThat(cart.getPrice()).isEqualTo(50000); // 가격이 맞는지 비교합니다.
        Assertions.assertThat(cart.getOption()).isEqualTo(optionDummy.get(optionDummyId)); // 옵션 더미 데이터와 비교합니다.

    }

    @Test
    public void save(){}

}
