package com.example.kakao.option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(ObjectMapper.class)
public class OptionJPARepositoryTest extends DummyEntity {
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;

    @BeforeEach
    public void setUp() {
        em.clear(); //마지막에 em.clear()를 시키면 장바구니 수정하는부분에서 Dirty Checking 이 불가능하기 때문에 처음으로 두었다.
        List<Product> productListPS = productJPARepository.saveAll(productDummyList2());
        List<Option> optionListPS = optionJPARepository.saveAll(optionDummyList2(productListPS));
    }

    @Test
    public void Option_findByProductId_fetch_join() throws JacksonException {
        //given
        int productId = 1;

        //when
        List<Option> optionPS = optionJPARepository.mFindByProductId(productId);
        String responseBody1 = om.writeValueAsString(optionPS);
        System.out.println("테스트 : " + responseBody1);
        em.close();

        //then
        Assertions.assertThat(optionPS.get(0).getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(optionPS.get(1).getPrice()).isEqualTo(10900);
    }
}
