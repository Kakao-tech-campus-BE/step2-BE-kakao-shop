package com.example.kakao.order;

import com.example.kakao.MyRestDoc;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest extends MyRestDoc {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private EntityManager em;
    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private ItemJPARepository itemJPARepository;
    @Autowired
    private ObjectMapper om;

    // (기능12) 결제하기 (주문 인서트)
    @Transactional
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void saveOrder() throws Exception {
        // 1. given
        int id = 1;
        List<Item> itemList = itemJPARepository.findAll();
        for(Item item : itemList){
            System.out.println("item 목룍: " + item.getOption().getId() + "\n");
        }
        OrderResponse.FindAllDTO requestDTO = new OrderResponse.FindAllDTO(itemList,id);
        System.out.println("item 그이후: " + requestDTO.getProducts().get(0).getProductName());

        String requestBody = om.writeValueAsString(requestDTO);
        System.out.println("requestBody: " + requestBody);


        // when
        ResultActions resultActions = mvc.perform(post("/orders/save")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("2"));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("4"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value("50000"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value("10900"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].id").value("6"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].price").value("250000"));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value("4"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value("50000"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].id").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].quantity").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].price").value("10900"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].id").value("6"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].price").value("250000"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value("1510900"));
        resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }

    // (기능10) 주문 결과 확인 GET
    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    public void findById() throws Exception {
        // given teardown.sql
        int id = 1;

        // when영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니
        //다.:password
        ResultActions resultActions = mvc.perform(get("/orders/" + id));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("order 테스트 : " + responseBody);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[0].price").value("50000"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].id").value("2"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].quantity").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[1].price").value("10900"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].id").value("3"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[0].items[2].price").value("250000"));
        resultActions.andExpect(jsonPath("$.response.products[1].productName").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].id").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[0].price").value("50000"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].id").value("2"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].quantity").value("1"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[1].price").value("10900"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].id").value("3"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].optionName").value("선택02_바른곡물효소누룽지맛 6박스"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].quantity").value("5"));
        resultActions.andExpect(jsonPath("$.response.products[1].items[2].price").value("250000"));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value("1510900"));
        resultActions.andExpect(jsonPath("$.error").value(IsNull.nullValue()));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);


    }
}
