package com.example.kakao.cart;

import com.example.kakao._core.security.JWTProvider;
import com.example.kakao._core.security.SecurityConfig;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import({
        FakeStore.class,
        SecurityConfig.class
})
@WebMvcTest(controllers = {CartRestController.class})
public class CartRestControllerTest {
    @MockBean
    private FakeStore fakeStore;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    public void setUp() throws Exception {
        // stub
        User user = new User(1, "ssar@nate.com", "1234", "ssar", "USER");

        List<Product> products = Arrays.asList(
                new Product(1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000),
                new Product(2, "[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000)
        );
        List<Option> options = Arrays.asList(
                new Option(1, products.get(0), "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
                new Option(2, products.get(0), "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
                new Option(3, products.get(0), "고무장갑 베이지 S(소형) 6팩", 9900),
                new Option(4, products.get(0), "뽑아쓰는 키친타올 130매 12팩", 16900),
                new Option(5, products.get(0), "2겹 식빵수세미 6매", 8900),
                new Option(6, products.get(1), "22년산 햇단밤 700g(한정판매)", 9900),
                new Option(7, products.get(1), "22년산 햇단밤 1kg(한정판매)", 14500),
                new Option(8, products.get(1), "밤깎기+다회용 구이판 세트", 5500)
        );
        Mockito.when(fakeStore.getCartList()).thenReturn(
                Arrays.asList(
                        new Cart(1, user, options.get(2), 3, options.get(2).getPrice()*3),
                        new Cart(2, user, options.get(6), 1, options.get(5).getPrice()*1)
                )
        );
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void update_test() throws Exception {
        // given
        List<CartRequest.UpdateDTO> requestDTOs = new ArrayList<>();
        CartRequest.UpdateDTO d1 = new CartRequest.UpdateDTO();
        d1.setCartId(1);
        d1.setQuantity(10);
        CartRequest.UpdateDTO d2 = new CartRequest.UpdateDTO();
        d2.setCartId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].cartId").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionId").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].optionName").value("고무장갑 베이지 S(소형) 6팩"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[0].price").value(9900*10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].cartId").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].optionId").value(7));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].optionName").value("22년산 햇단밤 1kg(한정판매)"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].quantity").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.carts[1].price").value(14500*10));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void add_test() throws Exception {
        // given
        List<CartRequest.SaveDTO> requestDTOs = new ArrayList<>();
        CartRequest.SaveDTO d1 = new CartRequest.SaveDTO();
        d1.setOptionId(1);
        d1.setQuantity(10);
        CartRequest.SaveDTO d2 = new CartRequest.SaveDTO();
        d2.setOptionId(2);
        d2.setQuantity(10);
        requestDTOs.add(d1);
        requestDTOs.add(d2);
        String requestBody = om.writeValueAsString(requestDTOs);
        System.out.println("테스트 : "+requestBody);

        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/add")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void findAll_test() throws Exception {
        // given


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.get("/carts")
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].option.id").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].quantity").value(3));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[0].carts[0].price").value(9900*3));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].carts[0].id").value(2));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].carts[0].option.id").value(7));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].carts[0].quantity").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.response.products[1].carts[0].price").value(14500*1));

        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }

    @WithMockUser(username = "ssar@nate.com", roles = "USER")
    @Test
    public void clear_test() throws Exception {
        // given


        // when
        ResultActions result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/carts/clear")
        );
        result.andDo(print());

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.error").value(nullValue()));
    }
}
