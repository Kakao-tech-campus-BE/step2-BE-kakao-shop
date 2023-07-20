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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity{

    @Autowired
    private EntityManager em;

    @Autowired
    private CartJPARepository cartJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        User userPS = userJPARepository.save(newUser("ssar"));
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(cartDummyList(userPS, optionListPS));
        em.clear();
    }

    @Test
    public void cart_findByUserId_test() throws JsonProcessingException{
        String email = "ssar@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );
        //LAZY 로딩으로 인한 hibernateLazyInitializer 오류 방지를 위해 option을 먼저 SELECT
        optionJPARepository.findAll();
        List<Cart> cartListPS = cartJPARepository.findByUserId(userPS.getId());

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : "+responseBody);

    }

    @Test
    public void cart_save_test() throws JsonProcessingException {
        // DummyEntity를 사용하지 않기 위해 신규 사용자 등록
        userJPARepository.save(newUser("temp"));
        String email = "temp@nate.com";
        User userPS = userJPARepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 이메일을 찾을 수 없습니다.")
        );
        List<Option> optionListPS = optionJPARepository.findAll();
        cartJPARepository.save(newCart(userPS, optionListPS.get(2), 4));
        cartJPARepository.save(newCart(userPS, optionListPS.get(3), 6));

        List<Cart> cartListPS = cartJPARepository.findByUserId(userPS.getId());

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(cartListPS);
        System.out.println("테스트 : "+responseBody);

    }
}
