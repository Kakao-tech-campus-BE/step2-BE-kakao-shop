package com.example.kakao.domain.cart.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@Builder
public class UpdateRequestDTO {
  @Positive(message = "카트 아이디는 1 이상이어야 합니다.")
  private int cartId;
  @Positive(message = "수량은 1 이상이어야 합니다.")
  private int quantity;
}