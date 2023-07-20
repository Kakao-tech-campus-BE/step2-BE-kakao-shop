package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
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


@Import({
        ObjectMapper.class,
        ProductService.class
})
@DataJpaTest
class ProductServiceTest extends DummyEntity {
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
    public void save_test() {
        // given
        List<Product> products = productDummyList();

        // when
        productService.saveAll(products);
    }

    @Test
    public void findAll_test() {
        // given
        int page = 0;
        int size = 9;

        // when
        List<Product> products = productService.findAll(page, size);

        // then
        Assertions.assertEquals(products.size(), size);
    }

    @Test
    public void findById_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Product product = productService.findById(id).orElseThrow(
                () -> new RuntimeException("제품을 찾을 수 없습니다.")
        );

        // then
        String result = om.writeValueAsString(product);
        System.out.println(result);
    }

    @Test
    public void option_findByProductId_test() throws JsonProcessingException {
        // given
        int id = 1;
        Product product = productService.findById(id).orElseThrow(
                () -> new RuntimeException("제품을 찾을 수 없습니다.")
        );

        // when
//        List<Option> options = optionJPARepository.findByProductId(product.getId());
        List<Option> options = productService.findOptionByProductId(product.getId());

        Assertions.assertFalse(options.isEmpty());

        // then
        String result = om.writeValueAsString(options);
        System.out.println(result);
    }
}