package com.example.kakao.domain.cart;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.cart.dto.response.FindAllResponseDTO;
import com.example.kakao.domain.product.ProductResponse;
import com.example.kakao.log.ErrorLogJPARepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@Sql("classpath:db/teardown.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CartJPARepositoryTest {
  @Autowired
  private CartJPARepository cartRepository;

  @Autowired
  private ObjectMapper om;

  @MockBean
  private ErrorLogJPARepository errorLogRepository;

  @Test
  void findAllByUserIdOrderByOptionIdAsc() throws JsonProcessingException {
    // given
    int userId = 1;

    // when
    FindAllResponseDTO response = new FindAllResponseDTO(
      cartRepository.findAllByUserIdOrderByOptionIdAsc(userId)
    );

    // eye
    System.out.println("response = " + om.writeValueAsString(response));

    // then
    assertThat(response.getProducts()).hasSize(2);
    assertThat(response.getProducts().get(0).getCarts()).hasSize(2);
    assertThat(response.getProducts().get(1).getCarts()).hasSize(1);
  }
}