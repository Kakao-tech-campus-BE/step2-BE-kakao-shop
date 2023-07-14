package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao._core.util.DummyEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

@Import(ObjectMapper.class)
@DataJpaTest
public class OptionJPARepositoryTest extends DummyEntity {

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
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        em.clear();
    }

    @Test
    public void option_findByProductId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Option> optionListPS = optionJPARepository.findByProductId(id);

        String responseBody = om.writeValueAsString(optionListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        // Add appropriate assertions for the Option entity
    }

    @Test
    public void option_mFindByProductId_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        List<Option> optionListPS = optionJPARepository.mFindByProductId(id);

        String responseBody = om.writeValueAsString(optionListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        // Add appropriate assertions for the Option entity
    }

    @Test
    public void option_findByProductId_with_product_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );
        List<Option> optionListPS = optionJPARepository.findByProductId(id);

        String responseBody1 = om.writeValueAsString(productPS);
        String responseBody2 = om.writeValueAsString(optionListPS);
        System.out.println("테스트 : "+responseBody1);
        System.out.println("테스트 : "+responseBody2);

        // then
        // Add appropriate assertions for the Product and Option entities
    }
}
