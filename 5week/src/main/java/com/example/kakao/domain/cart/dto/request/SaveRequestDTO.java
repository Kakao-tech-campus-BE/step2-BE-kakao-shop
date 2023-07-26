package com.example.kakao.domain.cart.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
public class SaveRequestDTO {
  @Positive(message = "옵션 아이디는 1 이상이어야 합니다.") @NotNull
  private int optionId;
  @Positive(message = "수량은 1 이상이어야 합니다.") @NotNull
  private int quantity;
}