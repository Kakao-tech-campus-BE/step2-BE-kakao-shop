package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;

@Import(ObjectMapper.class)
@DataJpaTest
public class ProductJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() {
        System.out.println("========================================setUp 시작=========================================");
        // product 더미데이터 생성
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        // option 더미데이터 생성
        optionJPARepository.saveAll(optionDummyList(productListPS));

        em.clear(); // PC 지우고 DB에 반영
    }

    @AfterEach
    public void resetIndex() {
        System.out.println("========================================resetIndex 시작=========================================");
        productJPARepository.deleteAll();
        optionJPARepository.deleteAll();

        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear(); // PC 지우고 DB에 반영
        System.out.println("========================================테스트 종료=========================================");
    }

    @DisplayName("전체 상품 조회 테스트")
    @Test
    public void product_findAll_test() throws JsonProcessingException {
        // given
        int page = 0;   // 첫 페이지
        int size = 9;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 페이지별 상품 조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPG = productJPARepository.findAll(pageRequest);
        // 페이지 유효성 검사
        if (productPG.isEmpty()) {
            throw new RuntimeException("전체 상품 조회 테스트 실패 : 페이지를 찾을 수 없습니다");
        }

        // then
        System.out.println("========================================테스트 결과=========================================");
        String responseBody = om.writeValueAsString(productPG);
        System.out.println(getPrettyString(responseBody));

        Assertions.assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
        Assertions.assertThat(productPG.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(productPG.getContent().get(0).getDescription()).isEqualTo("");
        Assertions.assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
        Assertions.assertThat(productPG.getTotalElements()).isEqualTo(15);  // 총 개수
        Assertions.assertThat(productPG.getTotalPages()).isEqualTo(2);  // 총 페이지수
        Assertions.assertThat(productPG.isLast()).isEqualTo(false);
        Assertions.assertThat(productPG.getSize()).isEqualTo(9);    // 한 페이지의 가능개수
        Assertions.assertThat(productPG.getNumber()).isEqualTo(0);  // 현재페이지
        Assertions.assertThat(productPG.isFirst()).isEqualTo(true);
        Assertions.assertThat(productPG.getNumberOfElements()).isEqualTo(9);    // 현재페이지의 개수
        Assertions.assertThat(productPG.isEmpty()).isEqualTo(false);
    }

    @DisplayName("특정 상품 조회 테스트")
    @Test
    public void option_mFindByProductId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        System.out.println("========================================테스트 시작=========================================");
        // 상품 유효성 검사
        productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("특정 상품 조회 테스트 실패 : 상품을 찾을 수 없습니다")
        );
        // 옵션 조회
        List<Option> optionListPS = optionJPARepository.mFindByProductId(id);

        // then
        System.out.println("========================================테스트 결과=========================================");
        String responseBody = om.writeValueAsString(optionListPS);
        System.out.println(getPrettyString(responseBody));

        Assertions.assertThat(optionListPS.get(0).getProduct().getId()).isEqualTo(1);
        Assertions.assertThat(optionListPS.get(0).getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(optionListPS.get(0).getProduct().getDescription()).isEqualTo("");
        Assertions.assertThat(optionListPS.get(0).getProduct().getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(optionListPS.get(0).getProduct().getPrice()).isEqualTo(1000);
        Assertions.assertThat(optionListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(optionListPS.get(0).getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(optionListPS.get(0).getPrice()).isEqualTo(10000);
    }
}