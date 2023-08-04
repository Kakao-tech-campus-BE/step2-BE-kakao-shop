package com.example.kakao.product;

import com.example.kakao.MyRestDoc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest extends MyRestDoc {

    @Test
    @DisplayName("상품 여러 개 보기")
    void find_products() throws Exception {
        mvc.perform(
                        get("/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response[0].description").value(""))
                .andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"))
                .andExpect(jsonPath("$.response[0].price").value(1000))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("상품 자세히보기 성공 테스트")
    void find_product_detail_success() throws Exception {
        mvc.perform(
                        get("/products/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.id").value(1))
                .andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .andExpect(jsonPath("$.response.description").value(""))
                .andExpect(jsonPath("$.response.image").hasJsonPath())
                .andExpect(jsonPath("$.response.price").value(1000))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    @DisplayName("상품 자세히보기 실패 테스트")
    void find_product_detail_fail() throws Exception {
        mvc.perform(
                        get("/products/100")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response").isEmpty())
                .andExpect(jsonPath("$.error.message").value("해당하는 상품이 없습니다."))
                .andExpect(jsonPath("$.error.status").value(404));
    }
}
