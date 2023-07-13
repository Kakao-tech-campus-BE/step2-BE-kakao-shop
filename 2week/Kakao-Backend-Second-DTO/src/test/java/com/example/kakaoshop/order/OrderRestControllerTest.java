package com.example.kakaoshop.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OrderRestControllerTest {
  @Autowired private MockMvc mvc;

  List<ResultMatcher> getSampleOrderMatcherList() {
    final int itemIds[] = {1, 2};
    final String itemNames[] = {"01. 슬라이딩 지퍼백 크리스마스에디션 4종", "02. 슬라이딩 지퍼백 플라워에디션 5종"};
    final int itemQuantities[] = {3, 5};
    final int itemPrices[] = {30000, 54500};

    List<ResultMatcher> matcherList =
        new ArrayList<>(
            List.of(
                status().is(200),
                jsonPath("$.success").value(true),
                jsonPath("$.response.id").value(1),
                jsonPath("$.response.totalPrice").value(Arrays.stream(itemPrices).sum()),
                jsonPath("$.response.products[0].productName")
                    .value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")));

    for (int i = 0; i != 2; ++i) {
      final var base = "$.response.products[0].items[" + i + "]";
      matcherList.addAll(
          List.of(
              jsonPath(base + ".id").value(itemIds[i]),
              jsonPath(base + ".optionName").value(itemNames[i]),
              jsonPath(base + ".quantity").value(itemQuantities[i]),
              jsonPath(base + ".price").value(itemPrices[i])));
    }
    return matcherList;
  }

  @Test
  @WithMockUser
  public void save_test() throws Exception {
    final ResultActions resultActions = mvc.perform(post("/orders/save"));
    final var response = resultActions.andReturn().getResponse();
    final var responseBody = response.getContentAsString();

    System.out.println(responseBody);

    for (final var resultMatcher : getSampleOrderMatcherList())
      resultActions.andExpect(resultMatcher);
  }

  @Test
  @WithMockUser
  public void getOrder_ok_test() throws Exception {
    final ResultActions resultActions = mvc.perform(get("/orders/1"));
    final var response = resultActions.andReturn().getResponse();
    final var responseBody = response.getContentAsString();

    System.out.println(responseBody);

    for (final var resultMatcher : getSampleOrderMatcherList())
      resultActions.andExpect(resultMatcher);
  }

  @Test
  @WithMockUser
  public void getOrder_not_found_test() throws Exception {
    mvc.perform(get("/orders/2"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.response").value((Object) null))
        .andExpect(jsonPath("$.error.status").value(404));
  }
}
