package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.ProductOption;
import com.example.kakao.product.option.ProductOptionJPARepository;
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
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private ProductOptionJPARepository productOptionJPARepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        List<Product> productList = productJPARepository.saveAll(productDummyList());
        productOptionJPARepository.saveAll(productOptionDummyList(productList));
        entityManager.clear();
    }

    @Test
    public void whenFindAllWithPaging_thenReturnProductPage() throws JsonProcessingException {
        // given
        int page = 0;
        int size = 9;

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPage = productJPARepository.findAllWithPaging(pageRequest);
        String responseBody = objectMapper.writeValueAsString(productPage);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(productPage.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(productPage.getSize()).isEqualTo(9);
        Assertions.assertThat(productPage.getNumber()).isEqualTo(0);
        Assertions.assertThat(productPage.getTotalElements()).isEqualTo(15);
        Assertions.assertThat(productPage.isFirst()).isEqualTo(true);
        Assertions.assertThat(productPage.getContent().get(0).getId()).isEqualTo(1);
        Assertions.assertThat(productPage.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(productPage.getContent().get(0).getDescription()).isEqualTo("");
        Assertions.assertThat(productPage.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(productPage.getContent().get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    public void whenFindByProductId_thenReturnProductOptionsEagerly() throws JsonProcessingException {
        // given
        int productId = 1;

        // when
        List<ProductOption> productOptions = productOptionJPARepository.findByProductId(productId);

        System.out.println("json 직렬화 직전========================");
        String responseBody = objectMapper.writeValueAsString(productOptions);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(productOptions.size()).isEqualTo(5);
        Assertions.assertThat(productOptions.get(1).getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(productOptions.get(2).getPrice()).isEqualTo(9900);
    }

    @Test
    public void whenFindByProductId_thenReturnProductOptionsLazily() throws JsonProcessingException {
        // given
        int productId = 1;

        // when
        List<ProductOption> productOptions = productOptionJPARepository.mFindByProductId(productId);

        System.out.println("json 직렬화 직전========================");
        String responseBody = objectMapper.writeValueAsString(productOptions);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertThat(productOptions.size()).isEqualTo(5);
        Assertions.assertThat(productOptions.get(1).getOptionName()).isEqualTo("02. 슬라이딩 지퍼백 플라워에디션 5종");
        Assertions.assertThat(productOptions.get(2).getPrice()).isEqualTo(9900);
    }

    @Test
    public void whenFindByIdAndFindByProductId_thenVerifyProductAndOptions() throws JsonProcessingException {
        // given
        int productId = 1;

        // when
        Product product = productJPARepository.findById(productId).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );
        List<ProductOption> productOptions = productOptionJPARepository.findByProductId(productId);

        String responseBody1 = objectMapper.writeValueAsString(product);
        String responseBody2 = objectMapper.writeValueAsString(productOptions);
        System.out.println("테스트 : " + responseBody1);
        System.out.println("테스트 : " + responseBody2);

        System.out.println(productOptions.get(0).getProduct().getProductName());

        // then
        Assertions.assertThat(product.getId()).isEqualTo(productId);
        Assertions.assertThat(productOptions.get(0).getProduct().getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    }


}
