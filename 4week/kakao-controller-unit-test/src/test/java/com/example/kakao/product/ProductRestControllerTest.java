package com.example.kakao.product;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



//@WebMvcTest(controllers = {ProductRestController.class})
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductRestControllerTest extends DummyEntity {

    @MockBean
    private ErrorLogJPARepository errorLogJPARepository;

    @Autowired
    private ProductJPARepository productJPARepository;

    @Autowired
    private OptionJPARepository optionJPARepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntityManager em;

    private final FakeStore fakeStore = new FakeStore();

    private List<Product> products;

    private List<Option> options;

    private int id;

    @BeforeEach
    public void setUp(){
        em.createNativeQuery("ALTER TABLE product_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE option_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        products = productJPARepository.saveAll(productDummyList());
        options = optionJPARepository.saveAll(optionDummyList(products));
    }


    @Test //멘토님의 피드백을 반영해서 JPARepository에 값을 저장한 후 id 값을 불러오게 만들어서 발전시킨 테스트 코드입니다.
    void findById_product_test() throws Exception{
        //given
        id = options.get(0).getId(); //Repository에 저장했던 id 값을 불러왔습니다.

        // Json 직렬화로 responseBody를 관찰하기 위한 코드
        Product product = fakeStore.getProductList().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        List<Option> optionList = fakeStore.getOptionList().stream().filter(option -> product.getId() == option.getProduct().getId()).collect(Collectors.toList());
        ProductResponse.FindByIdDTO responseDTO = new ProductResponse.FindByIdDTO(product, optionList);
        String requestBody = om.writeValueAsString(responseDTO);

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)

        );
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }
    @Test
    void findById_product_null_test() throws Exception{
        //given
        id = 1000; // product에 없는 id 값

        //when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .get("/products/" + id)

        );

        //then
        result.andExpectAll(
                status().is4xxClientError(), // 404 오류 확인
                MockMvcResultMatchers.jsonPath("$.success").value("false")
                );
    }
}
