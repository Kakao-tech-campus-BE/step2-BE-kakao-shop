package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

@Import({ObjectMapper.class, DummyEntity.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class ProductJPARepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DummyEntity dummyEntity;

    @BeforeAll
    void setUp() {
        List<Product> productListPS = productJPARepository.saveAll(dummyEntity.productDummyList());
        optionJPARepository.saveAll(dummyEntity.optionDummyList(productListPS));
        em.clear();
    }

    @Test
    void product_findAll_test() throws JsonProcessingException {
        // given
        int page = 0;
        int size = 9;

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPG = productJPARepository.findAll(pageRequest);
        String responseBody = om.writeValueAsString(productPG);
        System.out.println("테스트 : " + responseBody);

        // then
        Assertions.assertEquals(2, productPG.getTotalPages());
        Assertions.assertEquals(9, productPG.getSize());
        Assertions.assertEquals(0, productPG.getNumber());
        Assertions.assertEquals(15, productPG.getTotalElements());
        Assertions.assertEquals(true, productPG.isFirst());
        Assertions.assertEquals(1, productPG.getContent().get(0).getId());
        Assertions.assertEquals("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", productPG.getContent().get(0).getProductName());
        Assertions.assertEquals("", productPG.getContent().get(0).getDescription());
        Assertions.assertEquals("/images/1.jpg", productPG.getContent().get(0).getImage());
        Assertions.assertEquals(1000, productPG.getContent().get(0).getPrice());
    }

    @Test
    void option_findByProductId_lazy_error_test() {
        // given
        int id = 1;

        // when
        // option을 select했는데, product가 lazy여서 없는 상태이다.
        List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy

        // product가 없는 상태에서 json 변환을 시도하면 (hibernate는 select를 요청하는데, json mapper는 json 변환을 시도하게 된다)
        // 이때 json 변환을 시도하는 것이 타이밍적으로 더 빠르다 (I/O)가 없기 때문에!!
        // 그래서 hibernateLazyInitializer 오류가 발생한다.
        // 그림 설명 필요
        Assertions.assertThrows(JsonProcessingException.class, () ->
                om.writeValueAsString(optionListPS)
        );
    }

    // 추천
    // 조인쿼리 직접 만들어서 사용하기
    @Test
    void option_mFindByProductId_lazy_test() {
        // given
        int id = 1;

        // when
        List<Option> optionListPS = optionJPARepository.mFindByProductId(id); // Lazy


        Assertions.assertDoesNotThrow(() -> {
            System.out.println("json 직렬화 직전========================");
            String responseBody = om.writeValueAsString(optionListPS);
            System.out.println("json 직렬화");
        });
    }


    // 추천
    @Test
    void product_findById_and_option_findByProductId_lazy_test() {
        // given
        int id = 1;

        // when
        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );

        // product 상품은 영속화 되어 있어서, 아래에서 조인해서 데이터를 가져오지 않아도 된다.
        List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy

        Assertions.assertDoesNotThrow(() -> {
            String responseBody1 = om.writeValueAsString(productPS);
            String responseBody2 = om.writeValueAsString(optionListPS);
            System.out.println("테스트 : " + responseBody1);
            System.out.println("테스트 : " + responseBody2);
        });
    }
}
