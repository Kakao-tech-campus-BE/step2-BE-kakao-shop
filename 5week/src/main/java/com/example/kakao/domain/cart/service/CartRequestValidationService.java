package com.example.kakao.domain.cart.service;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CartRequestValidationService {

  public void validatePriceOverflow(long price) {
    if (price > CartPolicyManager.MAX_PRICE) throw new BadRequestException("장바구니에 담을 수 있는 최대 금액을 초과했습니다.");
  }

  public void validateUniqueOptionsInSaveDTO(List<SaveRequestDTO> requestDTOs) {
    if (requestDTOs.size() != requestDTOs.stream().mapToInt(SaveRequestDTO::getOptionId).distinct().count()) {
      throw new BadRequestException("요청 명세에 동일한 옵션이 2개 이상 존재합니다.");
    }
  }
  // 우발적 중복
  public void validateUniqueCartsInUpdateDTO(List<UpdateRequestDTO> requestDTOs) {
    if (requestDTOs.size() != requestDTOs.stream().mapToInt(UpdateRequestDTO::getCartId).distinct().count()) {
      throw new BadRequestException("요청 명세에 동일한 장바구니 아이디가 2개 이상 존재합니다.");
    }
  }

}
