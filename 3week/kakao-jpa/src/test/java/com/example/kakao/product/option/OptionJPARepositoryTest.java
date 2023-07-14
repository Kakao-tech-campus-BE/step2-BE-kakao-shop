package com.example.kakao.product.option;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Import(ObjectMapper.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class OptionJPARepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em; //테스트 쿼리 작성하기 위해서 추가하였음

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

        System.out.println("----------추가 완료---------");
    }


    @DisplayName("상품에 새 옵션 저장 테스트(쿼리작성) 및 지속성 검사")
    @Test
    public void newOption_addedToProduct_checkPersistence() {
        // given
        int productId = 1; // 설정해야 하는 상품 ID
        String newOptionName = "새 옵션을 추가하겠습니다.";

        Product product = productJPARepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Option newOption = newOption(product,newOptionName,10000);

        // when
        optionJPARepository.save(newOption);

        //영속성 컨텍스트 비워서 DB에서 가져오기
        em.clear();

        // then
        Option optionWithNewName = optionJPARepository.findByProductAndOptionName(product, newOptionName);

        String getNewName = optionWithNewName.getOptionName();
        Assertions.assertThat(newOptionName).isEqualTo(getNewName);

//        아래처럼 구현하면 N+1 문제가 발생할수도 있는 쿼리일까요?(Option마다의 name 또는 id 탐색)
//        List<Option> containsNewOption = optionJPARepository.findByProductId(productId);
//
//        boolean hasNewOption = containsNewOption.stream()
//                .anyMatch(option -> option.getOptionName().equals(newOptionName));

    }

    @DisplayName("상품 옵션 수정 테스트(판매자)")
    @Test
    public void existingOption_updatedInProduct_checkPersistence() {
        // given
        int productId = 1; // 설정해야 하는 상품 ID

        String existingOptionName = "01. 슬라이딩 지퍼백 크리스마스에디션 4종";
        String newOptionName = "새 옵션 이름";

        Product product = productJPARepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Option existingOption = optionJPARepository.findByProductAndOptionName(product, existingOptionName);

        System.out.println("변경하기 전 옵션명 : "+existingOption.getOptionName());
        // when
        existingOption.changeOptionName(newOptionName);

        System.out.println("변경후 옵션명 : "+existingOption.getOptionName());
        optionJPARepository.save(existingOption);
        
        
        //DB에 반영
        em.flush();
        // 영속성 컨텍스트 비워서 DB에서 가져오기
        em.clear();

        // then
        Product updatedProduct = productJPARepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Option updatedOption = optionJPARepository.findByProductAndOptionName(updatedProduct, newOptionName);

        String updatedName = updatedOption.getOptionName();
        Assertions.assertThat(newOptionName).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("상품 옵션 전부 삭제 이후 상품 삭제 테스트(판매자)")
    public void deleteAllOptionsAndProduct_checkPersistence() {
        // given
        int productId = 1; // 설정해야 하는 상품 ID

        Product product = productJPARepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        //join fetch 쿼리가 구현된 메서드로 옵션 전부 적재
        List<Option> options = optionJPARepository.mFindByProductId(productId);

        // when
        // 옵션 순회하며 삭제
        for (Option option : options) {
            optionJPARepository.delete(option);
        }

        // 영속성 컨텍스트를 비워서 DB에서 옵션 삭제를 반영
        em.flush();
        em.clear();

        // 상품도 삭제 (mFindProductId와 매개변수를 통일시키면 쿼리를 한번 줄일수 있을것 같다.)
        productJPARepository.delete(product);

        // 영속성 컨텍스트를 비워서 DB에서 상품 삭제를 반영
        em.flush();
        em.clear();

        // then

        // 삭제한 상품 찾기
        Optional<Product> deletedProduct = productJPARepository.findById(productId);

        // 삭제한 상품이 없으므로 Optional이 비어 있어야 한다.
        Assertions.assertThat(deletedProduct.isEmpty()).isTrue();

        // 삭제한 상품의 옵션 찾기
        List<Option> deletedOptions = optionJPARepository.mFindByProductId(productId);

        // 옵션들도 모두 삭제되었으므로 리스트가 비어 있어야 한다.
        Assertions.assertThat(deletedOptions.isEmpty()).isTrue();
    }





}
