package com.example.kakao.cart;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.order.OrderRestController;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList(productListPS));
        optionJPARepository.saveAll(optionDummyList(productListPS));
        cartJPARepository.saveAll(optionListPS);
        em.clear();
    }

    //장바구니 조회
    @Test
    public void cart_findAll_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Cart> cartList = cartJPARepository.findAll();

        // then
        Assertions.assertThat(cartList.get(1).getId()).isEqualTo(1);
        Assertions.assertThat(cartList.get(1).getOption().getId()).isEqualTo(0);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("01.슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(cartList.get(1).getOption().getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(cartList.get(1).getOption().getProduct().getPrice()).isEqualTo(10000);
        Assertions.assertThat(cartList.get(1).getOption().getId()).isEqualTo(2);
        Assertions.assertThat(cartList.get(1).getOption().getOptionName()).isEqualTo("JR310 (유선 전용) - 블루");
        Assertions.assertThat(cartList.get(1).getOption().getProduct().getProductName()).isEqualTo("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!");
        Assertions.assertThat(cartList.get(1).getOption().getProduct().getPrice()).isEqualTo(29900);
        Assertions.assertThat(cartList.get(1).getQuantity()).isEqualTo(2);
        Assertions.assertThat(cartList.get(1).getPrice()).isEqualTo(39900);

    }

    @Test
    // 장바구니에 상품 추가
    public void cartItem_update_test(){
        //given
        int id = 1;
        int product_id = 5;
        int option_id = 4;
        int quantity = 3;
        int price;

        //when
        Cart cartPS = cartJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 장바구니를 찾을 수 없습니다.")
        );
        System.out.println(cartPS.getId());

        cartPS.update(product_id,option_id,quantity,price);
        String responseBody = om.writeValueAsString(cartPS);
        System.out.println("테스트:"+responseBody)''

        // then

    }



}
