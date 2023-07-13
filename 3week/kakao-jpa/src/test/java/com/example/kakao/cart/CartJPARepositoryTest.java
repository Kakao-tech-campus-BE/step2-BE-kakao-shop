package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
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

    // test - 조회 , 수정
    // 조회
    @Test
    void cart_findAll_test() {
        // given
        int id = 1;

        // when
        List<Cart> cartListPS = cartJPARepository.findByUserId(id);
        // cartListPS.get(0) -> id, user, option, quantity, price
        // 기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전
        System.out.println(cartListPS.get(0).getOption().getProduct().getProductName());
        System.out.println(cartListPS.get(0).getPrice());
        // then ( 상태 검사 )

        // productId check
        Assertions.assertThat(cartListPS.get(0).getOption().getProduct().getId()).isEqualTo(1);
        // productName check
        Assertions.assertThat(cartListPS.get(0).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");

        // carts check
        Assertions.assertThat(cartListPS.get(0).getOption().getId()).isEqualTo(1);
        Assertions.assertThat(cartListPS.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartListPS.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartListPS.get(0).getQuantity()).isEqualTo(5);
        Assertions.assertThat(cartListPS.get(0).getPrice()).isEqualTo(50000);
    }

    // 수정
    @Test
    void cart_update_test() {
        // given
        int quantity = 10;
        int id = 1;

        List<Cart> cartListPS = cartJPARepository.findByUserId(id);

        // when
        cartJPARepository.updateQuantityById(cartListPS.get(0).getId(), quantity);
        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();
        // then ( 상태 검사 )
        List<Cart> updatedCartList = cartJPARepository.findByUserId(id);
        Assertions.assertThat(updatedCartList.get(0).getQuantity()).isEqualTo(quantity);
    }

}
