package com.example.kakao.domain.cart;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CartValidationService {

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

  public void validateExistingCartItem(List<UpdateRequestDTO> requestDTOs, List<Cart> cartList) {
    for (UpdateRequestDTO updateDTO : requestDTOs) {
      if (cartList.stream().noneMatch(cart -> cart.getId() == updateDTO.getCartId())) {
        throw new BadRequestException("유저의 장바구니에 존재하지 않는 cartId가 들어왔습니다 : " + updateDTO.getCartId());
      }
    }
  }

  public void validateNotEmptyCart(List<Cart> cartList) {
    if (cartList.isEmpty()) throw new BadRequestException("장바구니에 담긴 상품이 없습니다.");
  }
}
