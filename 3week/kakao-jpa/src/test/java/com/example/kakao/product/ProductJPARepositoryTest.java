package com.example.kakao.product;

import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.exception.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@Import(ObjectMapper.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class ProductJPARepositoryTest extends DummyEntity {

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
    }

    @DisplayName("상품 전체조회")
    @Test
    public void product_findAll_test() throws JsonProcessingException {
        // given
        int page = 0;
        int size = 9;

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> productPG = productJPARepository.findAll(pageRequest);
        String responseBody = om.writeValueAsString(productPG);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(productPG.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(productPG.getSize()).isEqualTo(9);
        Assertions.assertThat(productPG.getNumber()).isEqualTo(0);
        Assertions.assertThat(productPG.getTotalElements()).isEqualTo(15);
        Assertions.assertThat(productPG.isFirst()).isEqualTo(true);
        Assertions.assertThat(productPG.getContent().get(0).getId()).isEqualTo(1);
        Assertions.assertThat(productPG.getContent().get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(productPG.getContent().get(0).getDescription()).isEqualTo("");
        Assertions.assertThat(productPG.getContent().get(0).getImage()).isEqualTo("/images/1.jpg");
        Assertions.assertThat(productPG.getContent().get(0).getPrice()).isEqualTo(1000);
    }

    // LAZY 전략일시 테스트 에러 = Json 변환시 프록시 객체 내 직렬화 속성이 없기 때문에
    @DisplayName("Eager 조회 테스트")
    @Test
    public void option_findByProductId_eager_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        // 충분한 데이터 - product만 0번지에서 빼면  된다
        // 조인은 하지만, fetch를 하지 않아서, product를 한번 더 select 했다.
        List<Option> optionListPS = optionJPARepository.findByProductId(id); // Eager

        Throwable thrown = catchThrowable(() -> {
            System.out.println("json 직렬화 직전========================");
            String responseBody = om.writeValueAsString(optionListPS);
            System.out.println("테스트 : "+responseBody);
        });


        // then
        Assertions.assertThat(thrown).isInstanceOf(InvalidDefinitionException.class);
    }

    //EAGER 전략일시 테스트 에러
    @DisplayName("Lazy 예외 발생 테스트")
    @Test
    public void option_findByProductId_lazy_error_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        // option을 select했는데, product가 lazy여서 없는 상태이다.
        List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy
        Throwable thrown = catchThrowable(() -> {
            System.out.println("json 직렬화 직전========================");
            String responseBody = om.writeValueAsString(optionListPS);
            System.out.println("테스트 : "+responseBody);
        });

        // then
        Assertions.assertThat(thrown).isInstanceOf(JsonProcessingException.class);
    }

    // 추천
    // 조인쿼리 직접 만들어서 사용하기
    @DisplayName("사용자 정의쿼리 Lazy 조회 테스트")
    @Test
    public void option_mFindByProductId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Query query = em.createQuery(
                "SELECT o FROM Option o JOIN FETCH o.product WHERE o.product.id = :id"
        );
        query.setParameter("id", id);
        List<Option> optionListPS = query.getResultList();
//        List<Option> optionListPS = optionJPARepository.mFindByProductId(id); // Lazy

        System.out.println("json 직렬화 직전========================");
        String responseBody = om.writeValueAsString(optionListPS);
        System.out.println("테스트 : "+responseBody);

        // then
        Assertions.assertThat(optionListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(optionListPS.get(0).getProduct().getImage()).isEqualTo("/images/1.jpg");

    }


    // 추천
    @DisplayName("Product 조회후 연결된 Option Lazy 조회 테스트")
    @Test
    public void product_findById_and_option_findByProductId_lazy_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );

        // product 상품은 영속화 되어 있어서, 아래에서 조인해서 데이터를 가져오지 않아도 된다.
        List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy

        String responseBody1 = om.writeValueAsString(productPS);
        String responseBody2 = om.writeValueAsString(optionListPS);
        System.out.println("테스트 : "+responseBody1);
        System.out.println("테스트 : "+responseBody2);

        // then
        Assertions.assertThat(productPS.getId()).isEqualTo(1);
        Assertions.assertThat(productPS.getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
        Assertions.assertThat(productPS.getDescription()).isEqualTo("");
        Assertions.assertThat(optionListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(optionListPS.get(0).getProduct().getImage()).isEqualTo("/images/1.jpg");



    }

    //@ManyToOne 관계에서 Option 삭제되어도 Product 남아있어야함
    @DisplayName("옵션 삭제 후 상품 지속성 검사")
    @Test
    public void option_deleted_checkProductPersistence() {
        // given
        int optionId = 1; //id;1 인 상품의 id;1인 옵션 제거할 예정

        Option option = optionJPARepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("옵션을 찾을 수 없습니다."));

        int productId = option.getProduct().getId();

        // when
        optionJPARepository.deleteById(optionId);

        // then
        // Option이 삭제된 후에도 Product가 여전히 존재해야 합니다.
        Assertions.assertThat(productJPARepository.existsById(productId)).isTrue();
    }

    @DisplayName("상품 삭제 에러 테스트")
    @Test
    public void manyOptions_LinkedToProduct_delete_test(){

        //given
        int optionId = 1;

        Option option = optionJPARepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("옵션을 찾을 수 없습니다."));

        //옵션의 product로드 후에 그 id를 가져온다. = pc에서 가져온다.
        int productId = option.getProduct().getId();

        //when
        //이렇게 구현하면 product는 db에서 사라졌지만 pc에서는 존재한다.
        Throwable thrown = catchThrowable(() -> {
            productJPARepository.deleteById(productId);
            //pc 변경사항을 데이터베이스에 반영
            em.flush();
            //pc 초기화
            em.clear();
        });

        //then
        //상품의 옵션들이 남아있기에 바로 Product를 사용하지 못한다.
        //1. 양방향 매핑 즉 cascadeType을 이용하면 product만 삭제해도 삭제될텐데.. 양방향 매핑을 도입하는것이 db관리 차원에서 좋은지 궁금합니다.

        //2. 상위 수준의 예외를 검사하는 경우와 내부 원인까지 검사해야되는 경우 어떤게 적절한지 궁금합니다.
//        Assertions.assertThat(thrown).isInstanceOf(PersistenceException.class);
        Assertions.assertThat(thrown.getCause()).isInstanceOf(ConstraintViolationException.class);

    }

    @DisplayName("상품 수정시 옵션 유지 테스트")
    @Test
    public void update_product_OptionRemain_test() throws JsonProcessingException {
        // given
        int id = 1;

        // when
        Product productPS = productJPARepository.findById(id).orElseThrow(
                () -> new RuntimeException("상품을 찾을 수 없습니다")
        );

        productPS.setProductName("상품이름 변경테스트");
        productJPARepository.save(productPS);

        // product 상품은 영속화 되어 있어서, 아래에서 조인해서 데이터를 가져오지 않아도 된다.
        List<Option> optionListPS = optionJPARepository.findByProductId(id); // Lazy

        String responseBody1 = om.writeValueAsString(productPS);
        String responseBody2 = om.writeValueAsString(optionListPS);
        System.out.println("테스트 : "+responseBody1);
        System.out.println("테스트 : "+responseBody2);

        // then
        Assertions.assertThat(productPS.getId()).isEqualTo(1);
        Assertions.assertThat(productPS.getProductName()).isEqualTo("상품이름 변경테스트");
        Assertions.assertThat(productPS.getDescription()).isEqualTo("");
        Assertions.assertThat(optionListPS.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(optionListPS.get(0).getProduct().getImage()).isEqualTo("/images/1.jpg");


    }



}
