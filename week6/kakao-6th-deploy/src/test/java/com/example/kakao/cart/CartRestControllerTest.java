package com.example.kakao.cart;

import com.example.kakao.MyRestDoc;
import com.example.kakao.cart.CartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.kakao._core.utils.PrintUtils.getPrettyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartRestControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 담기 성공")
    public void addCartList_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(3);
        item1.setQuantity(5);
        requestDTOs.add(item1);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(4);
        item2.setQuantity(5);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("이미 추가한 옵션이면 수량 수정 성공")
    public void addCartList_quantityUpdate_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(1);
        item1.setQuantity(10);
        requestDTOs.add(item1);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(2);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("동일한 옵션을 요청하면 실패")
    public void addCartList_badRequest_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(3);
        item1.setQuantity(5);
        requestDTOs.add(item1);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(3);
        item2.setQuantity(5);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("유효하지 않은 옵션이면 실패")
    public void addCartList_notFound_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();

        CartRequest.SaveDTO item1 = new CartRequest.SaveDTO();
        item1.setOptionId(3);
        item1.setQuantity(5);
        requestDTOs.add(item1);

        CartRequest.SaveDTO item2 = new CartRequest.SaveDTO();
        item2.setOptionId(100);
        item2.setQuantity(5);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isNotFound()); // 404

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 조회 성공")
    public void findAll_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/carts")
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.id").value(1));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.price").value(10000));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].quantity").value(5));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].price").value(50000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(310900));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("장바구니 수정 성공")
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();

        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(10);
        requestDTOs.add(item1);

        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(2);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.carts[0].cartId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionId").value("1"));
        resultActions.andExpect(jsonPath("$.response.carts[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.carts[0].quantity").value(10));
        resultActions.andExpect(jsonPath("$.response.carts[0].price").value(100000));
        resultActions.andExpect(jsonPath("$.response.totalPrice").value(459000));

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("동일한 장바구니아이템을 요청하면 실패")
    public void update_badRequest_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();

        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(10);
        requestDTOs.add(item1);

        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(1);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isBadRequest()); // 400

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "another@nate.com")
    @Test
    @DisplayName("장바구니가 비어있으면 실패")
    public void update_isEmpty_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();

        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(10);
        requestDTOs.add(item1);

        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(2);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isNotFound()); // 404

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @WithUserDetails(value = "ssarmango@nate.com")
    @Test
    @DisplayName("유효하지 않은 장박구니아이템이면 실패")
    public void update_notFound_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();

        CartRequest.UpdateDTO item1 = new CartRequest.UpdateDTO();
        item1.setCartId(1);
        item1.setQuantity(10);
        requestDTOs.add(item1);

        CartRequest.UpdateDTO item2 = new CartRequest.UpdateDTO();
        item2.setCartId(100);
        item2.setQuantity(10);
        requestDTOs.add(item2);

        // json 변환
        String requestBody = om.writeValueAsString(requestDTOs);

        // eye
        System.out.println("===============requestBody 시작===============");
        System.out.println(getPrettyString(requestBody));
        System.out.println("===============requestBody 종료===============");

        // when
        ResultActions resultActions = mvc.perform(
                post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("===============responseBody 시작===============");
        System.out.println(getPrettyString(responseBody));
        System.out.println("===============responseBody 종료===============");

        // then
        resultActions.andExpect(status().isNotFound()); // 404

        // API
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}