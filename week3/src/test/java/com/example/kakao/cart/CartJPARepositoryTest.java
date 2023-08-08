package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
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

@Import(ObjectMapper.class)
@DataJpaTest
public class CartJPARepositoryTest extends DummyEntity {

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

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE cart_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        List<Product> productListPS = productJPARepository.saveAll(productDummyList()); //상품 리스트 저장
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS)); //옵션 리스트 저장
        userJPARepository.save(userDummy);
        cartJPARepository.saveAll(cartDummyList(optionListPS)); //장바구니 리스트 저장
        em.clear(); //쿼리 보기 위해 PC clear
    }

    @Test
    @DisplayName("1. 장바구니 조회 (전체 조회)")
    public void cart_findAll_test() throws JsonProcessingException {
        // given

        // when
        //다중 join fetch, select문 총 2번 수행(1번: join fetch, 1번:option의 product 조회)
        List<Cart> cartPG = cartJPARepository.findFetchAllWithUserAndOption();
        String responseBody = om.writeValueAsString(cartPG); //직렬화하여 출력
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cartPG.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(cartPG.get(0).getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartPG.get(0).getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartPG.get(0).getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("2. 장바구니 담기 (저장)")
    public void add_cart_test() throws JsonProcessingException {
        // given
        System.out.println("장바구니 담기 전");
        Cart cart = newCart(userDummy, optionDummyList(productDummyList()).get(0), 3);
        // when
        //insert 쿼리 1번
        cartJPARepository.save(cart); //저장
        String responseBody = om.writeValueAsString(cart); //직렬화하여 출력
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(cart.getId()).isEqualTo(3); //dummy로 cart 이미 2개 존재하므로 id는 3
        Assertions.assertThat(cart.getOption().getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cart.getOption().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cart.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("3.주문하기 (장바구니 수정)")
    public void update_cart_test() throws JsonProcessingException {
        // given
        Cart cart = cartJPARepository.findFetchAllWithUserAndOption().get(0);
        int id = cart.getId();
        // when
        // select 쿼리 2번(join fetch 1번+ option의 Product 조회 1번) + update 쿼리 1번
            cart.update(10); //장바구니 수정

            String responseBody = om.writeValueAsString(cart); //직렬화하여 출력
            System.out.println("테스트 : "+responseBody);

            //then
            Assertions.assertThat(cart.getId()).isEqualTo(id);
            Assertions.assertThat(cart.getQuantity()).isEqualTo(10);
    }

}
