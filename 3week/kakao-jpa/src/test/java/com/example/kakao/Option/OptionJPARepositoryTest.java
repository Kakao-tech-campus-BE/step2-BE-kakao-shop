package com.example.kakao.Option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.product.option.OptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Import(ObjectMapper.class)
@DataJpaTest
public class OptionJPARepositoryTest extends DummyEntity {
    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ObjectMapper om;
    @Autowired
    private ProductJPARepository productJPARepository;

    @BeforeEach
    public void setUp(){
        List<Product> productListPS = productJPARepository.saveAll(productDummyList());
        optionJPARepository.saveAll(optionDummyList(productListPS));
        em.clear();
    }

    @Test //상품번호로 옵션 검색(Lazy Loading으로 Proxy에 담긴 Product 객체를 DTO로 옮김)
    public void option_findByProductId_test() throws JsonProcessingException {
        //given
        int productId=1;

        //when
        System.out.println("json 직렬화 직전========================");
        List<Option> findOptions=optionJPARepository.findByProductId(productId);
        OptionResponse.FindByProductIdDTO findByProductIdDTO=new OptionResponse.FindByProductIdDTO(findOptions);
        String responseBody=om.writeValueAsString(findByProductIdDTO);
        System.out.println("테스트 : "+responseBody);

        //then
        Assertions.assertThat(findOptions.get(0).getProduct().getId()).isEqualTo(productId);
        Assertions.assertThat(findOptions.size()).isEqualTo(5);
    }

    @Test //옵션 번호로 검색
    public void option_findById_test(){
        //given
        int id=1;
        //when
        Option findOption=optionJPARepository.findById(id).get();
        //then
        Assertions.assertThat(findOption.getId()).isEqualTo(id);
        Assertions.assertThat(findOption.getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
        Assertions.assertThat(findOption.getPrice()).isEqualTo(10000);
    }
}
