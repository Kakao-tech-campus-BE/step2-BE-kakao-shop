package com.example.kakao.product.option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Import({ObjectMapper.class, DummyEntity.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class OptionJPARepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private DummyEntity dummyEntity;

    @Autowired
    private ObjectMapper om;

    @BeforeAll
    void setUp() {
        List<Product> products = dummyEntity.productDummyList();
        productJPARepository.saveAll(products);
        optionJPARepository.saveAll(dummyEntity.optionDummyList(products));
        em.clear();
    }

    @Test
    @DisplayName("fetch join시 연관된 데이터를 한 번에 불러온다.")
    void option_mfindById_test() {
        // given
        int optionId = 1;

        // when
        Optional<Option> option = optionJPARepository.mFindByOptionId(optionId);

        // then
        Assertions.assertDoesNotThrow(() -> {
            om.writeValueAsString(option);
        });
    }

    @Test
    void option_by_id() {
        int optionId = 1;

        Option option = optionJPARepository.findById(optionId).get();

        Assertions.assertThrows(JsonProcessingException.class, () -> om.writeValueAsString(option));
    }
}