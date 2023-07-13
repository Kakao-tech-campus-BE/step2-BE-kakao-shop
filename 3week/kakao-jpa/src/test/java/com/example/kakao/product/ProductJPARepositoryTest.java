package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class ProductJPARepositoryTest extends DummyEntity {
  @Autowired private EntityManager em;
  @Autowired private ProductJPARepository productJPARepository;
  @Autowired private OptionJPARepository optionJPARepository;
  @Autowired private ObjectMapper om;

  @BeforeEach
  public void setUp() {
    em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();

    List<Product> productListPS = productJPARepository.saveAll(productDummyList());
    optionJPARepository.saveAll(optionDummyList(productListPS));
    em.clear();
    System.out.println("=============");
  }

  @Test
  public void product_findAll_test() throws JsonProcessingException {
    // given
    int page = 0;
    int size = 9;

    // when
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Product> productPG = productJPARepository.findAll(pageRequest);
    String responseBody = om.writeValueAsString(productPG);
    System.out.println("테스트 : " + responseBody);

    // then
    Assertions.assertThat(productPG.getTotalPages()).isEqualTo(2);
    Assertions.assertThat(productPG.getSize()).isEqualTo(9);
    Assertions.assertThat(productPG.getNumber()).isEqualTo(0);
    Assertions.assertThat(productPG.getTotalElements()).isEqualTo(15);
    Assertions.assertThat(productPG.isFirst()).isEqualTo(true);
    Assertions.assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
    Assertions.assertThat(productPG.getContent().get(0).getProductName())
        .isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    Assertions.assertThat(productPG.getContent().get(0).getDescription()).isEqualTo("");
    Assertions.assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
    Assertions.assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
  }

  // ManyToOne 전략을 Eager로 간다면 추천
  @Test
  public void option_findByProductId_test() throws JsonProcessingException {
    // given
    int id = 1;

    // when
    // 충분한 데이터 - product만 0번지에서 빼면  된다
    // 조인은 하지만, fetch를 하지 않아서, product를 한번 더 select 했다.
    List<Option> optionListPS = optionJPARepository.findByProductId(id); // Eager

    System.out.println("json 직렬화 직전========================");
    String responseBody = om.writeValueAsString(optionListPS);
    System.out.println("테스트 : " + responseBody);

    // then
    Assertions.assertThat(optionListPS.size()).isEqualTo(5);

    // 0
    Assertions.assertThat(optionListPS.get(0).getId()).isEqualTo(1);
    Assertions.assertThat(optionListPS.get(0).getOptionName())
        .isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    Assertions.assertThat(optionListPS.get(0).getPrice()).isEqualTo(10000);

    // 1
    Assertions.assertThat(optionListPS.get(1).getId()).isEqualTo(2);
    Assertions.assertThat(optionListPS.get(1).getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
    Assertions.assertThat(optionListPS.get(1).getPrice()).isEqualTo(10900);

    // 2
    Assertions.assertThat(optionListPS.get(2).getId()).isEqualTo(3);
    Assertions.assertThat(optionListPS.get(2).getOptionName()).isEqualTo("고무장갑 베이지 S(소형) 6팩");
    Assertions.assertThat(optionListPS.get(2).getPrice()).isEqualTo(9900);

    // 3
    Assertions.assertThat(optionListPS.get(3).getId()).isEqualTo(4);
    Assertions.assertThat(optionListPS.get(3).getOptionName()).isEqualTo("뽑아쓰는 키친타올 130매 12팩");
    Assertions.assertThat(optionListPS.get(3).getPrice()).isEqualTo(16900);

    // 4
    Assertions.assertThat(optionListPS.get(4).getId()).isEqualTo(5);
    Assertions.assertThat(optionListPS.get(4).getOptionName()).isEqualTo("2겹 식빵수세미 6매");
    Assertions.assertThat(optionListPS.get(4).getPrice()).isEqualTo(8900);

    // each product test
    for (Option option : optionListPS) {
      Product product = option.getProduct();
      Assertions.assertThat(product.getId()).isEqualTo(1);
      Assertions.assertThat(product.getProductName())
          .isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
      Assertions.assertThat(product.getDescription()).isEqualTo("");
      Assertions.assertThat(product.getImage()).isEqualTo("/images/1.jpg");
      Assertions.assertThat(product.getPrice()).isEqualTo(1000);
    }
  }

  // 추천
  // 조인쿼리 직접 만들어서 사용하기
  @Test
  public void option_mFindByProductId_lazy_test() throws JsonProcessingException {
    // given
    int id = 1;

    // when
    List<Option> optionListPS = optionJPARepository.mFindByProductId(id); // Lazy

    System.out.println("json 직렬화 직전========================");
    String responseBody = om.writeValueAsString(optionListPS);
    System.out.println("테스트 : " + responseBody);

    // then
      Assertions.assertThat(product.getPrice()).isEqualTo(1000);
    }
  }

  // 추천
  @Test
  public void product_findById_and_option_findByProductId_lazy_test()
      throws JsonProcessingException {
    // given
    int id = 1;

    // when
    Product productPS =
        productJPARepository.findById(id).orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다"));

    // product 상품은 영속화 되어 있어서, 아래에서 조인해서 데이터를 가져오지 않아도 된다.
    List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy

    String responseBody1 = om.writeValueAsString(productPS);
    String responseBody2 = om.writeValueAsString(optionListPS);
    System.out.println("테스트 : " + responseBody1);
    System.out.println("테스트 : " + responseBody2);

    // then
  }
}
