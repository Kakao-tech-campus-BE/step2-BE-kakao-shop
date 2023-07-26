package com.example.kakao.domain.cart.dto.request;

import com.example.kakao.domain.cart.CartPolicyManager;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
public class UpdateRequestDTO {
  @Positive(message = "카트 아이디는 1 이상이어야 합니다.") @NotNull
  private int cartId;
  @Positive(message = "수량은 1 이상이어야 합니다.") @NotNull
  @Max(value = CartPolicyManager.MAX_QUANTITY, message = "수량은 " + CartPolicyManager.MAX_QUANTITY + "개 이하이어야 합니다.") // 수량 합 검사는 Service 에서 별도로.
  private int quantity;
}