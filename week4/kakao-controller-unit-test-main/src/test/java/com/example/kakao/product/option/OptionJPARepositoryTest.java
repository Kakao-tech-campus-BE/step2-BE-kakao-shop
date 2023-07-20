package com.example.kakao.product.option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import({
        ObjectMapper.class,
        ProductService.class
})
@DataJpaTest
class OptionJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductService productService;
    @Autowired
    private OptionJPARepository optionJPARepository;
    @Autowired
    private ObjectMapper om;


    @BeforeEach
    public void setup() {
        System.out.println("-----before 시작-----");
        List<Product> products = productDummyList();
        products = productService.saveAll(products);
        optionJPARepository.saveAll(optionDummyList(products));
        em.clear();
        System.out.println("-----before 완료-----");
    }

    @AfterEach
    public void reset() {
        System.out.println("-----after 시작-----");
        optionJPARepository.deleteAll();
        productService.deleteAll();
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
        System.out.println("-----after 완료-----");
    }

    @Test
    public void findById_test() throws JsonProcessingException {
        // given
        int option_id = 1;

        // when
        Option option = optionJPARepository.findById(option_id).orElseThrow(
                () -> new RuntimeException("option id 에 해당하는 option을 찾을 수 없습니다.")
        );

        // then
        String result = om.writeValueAsString(option);
        System.out.println(result);
    }

    @Test
    public void findByProductId_test() throws JsonProcessingException {
        // given
        int product_id = 1;

        // when
        List<Option> options = optionJPARepository.findByProductId(product_id);
        Assertions.assertFalse(options.isEmpty());

        // then
        String result = om.writeValueAsString(options);
        System.out.println(result);
    }

    @Test
    public void deleteByProductId_test() {
        // given
        int product_id = 1;

        System.out.println("삭제 전 option 개수 : " + optionJPARepository.findByProductId(product_id).size());

        // when
        optionJPARepository.deleteByProductId(product_id);

        // then
        System.out.println("삭제 후 option 개수 : " + optionJPARepository.findByProductId(product_id).size());
    }
}