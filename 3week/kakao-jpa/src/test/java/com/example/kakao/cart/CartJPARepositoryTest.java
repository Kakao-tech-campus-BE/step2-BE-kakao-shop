package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
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
public class CartJPARepositoryTest extends DummyEntity {
    // 엔티티 관리
    @Autowired
    private EntityManager entityManager;
    // user JPA
    @Autowired
    private UserJPARepository userJPARepository;
    // option JPA
    @Autowired
    private OptionJPARepository optionJPARepository;
    // product JPA
    @Autowired
    private ProductJPARepository productJPARepository;
    // cart JPA
    @Autowired
    private CartJPARepository cartJPARepository;
    // 파싱
    @Autowired
    private ObjectMapper objectMapper;

    // setup
    @BeforeEach
    public void setUp(){
        // id 값 리셋
        entityManager.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

        // 아이디
        User user = newUser("ssar");
        userJPARepository.save(user);

        // 물품
        List<Product> products = productDummyList();
        productJPARepository.saveAll(products);

        // 옵션
        List<Option> options = optionDummyList(products);
        optionJPARepository.saveAll(options);

        // 카트
        List<Cart> carts = cartDummyList(user, options);
        cartJPARepository.saveAll(carts);

        // 영속성 컨텍스트 비우기
        entityManager.clear();
    }

    // test - 조회 ( carts ) , 수정 ( update ) , 담기 ( add )
    @Test
    public void Test() {

    }



}
