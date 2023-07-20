package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@Import(ObjectMapper.class)
@DataJpaTest
class ProductJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp(){
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productList));

        em.clear();
    }

    @AfterEach
    void clear(){
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    @DisplayName("전체 상품 조회")
    void product_findAll_test() throws JsonProcessingException {
        // given
        int page = 0;
        int size = 9;

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPG = productJPARepository.findAll(pageRequest);
        String responseBody = om.writeValueAsString(productPG);
        log.info("responseBody=" + responseBody);

        // then
        assertThat(productPG.getTotalPages()).isEqualTo(2);
        assertThat(productPG.getSize()).isEqualTo(9);
        assertThat(productPG.getNumber()).isZero();
        assertThat(productPG.getTotalElements()).isEqualTo(15);
        assertThat(productPG.isFirst()).isTrue();
        assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
        assertThat(productPG.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        assertThat(productPG.getContent().get(0).getDescription()).isEmpty();
        assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
        assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("개별 상품 조회 성공")
    void option_findByProductId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Option> optionList = optionJPARepository.findByProductId(id);
        log.info("========== JSON 직렬화 ==========");
        String responseBody = om.writeValueAsString(optionList);
        log.info("responseBody=" + responseBody);

        // then
        assertThat(optionList).hasSize(5);
        assertThat(optionList.get(0).getId()).isEqualTo(1); //optionId
        assertThat(optionList.get(0).getProduct().getId()).isEqualTo(1); //productId
    }

    @Test
    @DisplayName("개별 상품 조회 실패")
    void option_findByProductId_fail_test() throws JsonProcessingException {
        // given
        int id = 100;

        // when
        List<Option> optionList = optionJPARepository.findByProductId(id);
        log.info("========== JSON 직렬화 ==========");
        String responseBody = om.writeValueAsString(optionList);
        log.info("responseBody=" + responseBody);

        //then
        assertThat(optionList).isEmpty();
    }

    @Test
    @DisplayName("개별 상품 조회 - fetch join")
    void option_findByProductId_joinFetch_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Option> optionList = optionJPARepository.mFindByProductId(id);
        log.info("========== JSON 직렬화 ==========");
        String responseBody = om.writeValueAsString(optionList);
        log.info("responseBody=" + responseBody);

        // then
        assertThat(optionList).hasSize(5);
        assertThat(optionList.get(0).getId()).isEqualTo(1); //optionId
        assertThat(optionList.get(0).getProduct().getId()).isEqualTo(1); //productId
    }

    @Test
    @DisplayName("개별 상품 조회 - 상품, 옵션 각각 조회")
    void product_findById_option_findByProductId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Product product = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );
        List<Option> optionList = optionJPARepository.findByProductId(id);

        String responseBody1 = om.writeValueAsString(product);
        String responseBody2 = om.writeValueAsString(optionList);
        log.info("responseBody1=" + responseBody1);
        log.info("responseBody2=" + responseBody2);

        // then
        assertThat(optionList).hasSize(5);
        assertThat(optionList.get(0).getId()).isEqualTo(1); //optionId
        assertThat(optionList.get(0).getProduct().getId()).isEqualTo(1); //productId
    }
}
