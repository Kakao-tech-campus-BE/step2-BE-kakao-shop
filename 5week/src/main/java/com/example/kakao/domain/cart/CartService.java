package com.example.kakao.domain.cart;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao._core.errors.exception.NotFoundException;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import com.example.kakao.domain.cart.dto.response.FindAllResponseDTO;
import com.example.kakao.domain.cart.dto.response.UpdateResponseDTO;
import com.example.kakao.domain.product.option.Option;
import com.example.kakao.domain.product.option.OptionJPARepository;
import com.example.kakao.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
  private final CartJPARepository cartRepository;
  private final OptionJPARepository optionRepository;

  @Transactional
  public void addCartList(List<SaveRequestDTO> requestDTOs, User sessionUser) {
    validateUniqueOptionsInSaveDTO(requestDTOs); // Dto 에 동일한 옵션이 2개 이상 존재하면 예외처리

    for (SaveRequestDTO requestDTO : requestDTOs) {
      int optionId = requestDTO.getOptionId();
      int quantity = requestDTO.getQuantity();
      Option option = findValidOption(optionId);

      Optional<Cart> existingCartItem = cartRepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
      // 이미 장바구니에 존재하면 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
      if(existingCartItem.isPresent()){
        updateExistingCartItem(quantity, option, existingCartItem.get());
        continue;
      }
      createNewCartItem(sessionUser, quantity, option);
    }
  }


  public FindAllResponseDTO findAll(User user) {
    List<Cart> cartList = cartRepository.findAllByUserIdOrderByOptionIdAsc(user.getId());
    // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다. -> 투박하지만 짧고 효과적인 설명.
    return new FindAllResponseDTO(cartList);
  }



  // TODO : 너무 복잡함.
  @Transactional
  public UpdateResponseDTO update(List<UpdateRequestDTO> requestDTOs, User user) {
    List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

    if(cartList.isEmpty()) throw new BadRequestException("장바구니에 담긴 상품이 없습니다.");

    validateUniqueCartsInUpdateDTO(requestDTOs);

    validateExistingCartItem(requestDTOs, cartList);

    for (Cart cart : cartList) {
      for (UpdateRequestDTO updateDTO : requestDTOs) {
        if (cart.getId() == updateDTO.getCartId()) {
          if(updateDTO.getQuantity() == 0){
            cartRepository.delete(cart);
            continue;
          }

          cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
        }
      }
    }

    return new UpdateResponseDTO(cartList);
  } // 더티체킹



  // 이 부분이 굉장히 불만입니다. Public 과 Private 이 나눠지는 영역을 훨씬 쉽게 관리하고 눈으로 파악하기도 쉬워야 한다고 생각합니다.
  // 좋은 방법이 있을까요?
  // ----------------- private -----------------

  private void createNewCartItem(User sessionUser, int quantity, Option option) {
    int price = option.getPrice() * quantity;
    Cart cart = Cart.builder()
      .user(sessionUser)
      .option(option)
      .quantity(quantity)
      .price(price)
      .build();
    cartRepository.save(cart);
  }

  private void updateExistingCartItem(int quantity, Option option, Cart cart) {
    cart.update(cart.getQuantity() + quantity, cart.getPrice() + option.getPrice() * quantity);
  }

  private static void validateExistingCartItem(List<UpdateRequestDTO> requestDTOs, List<Cart> cartList) {
    for (UpdateRequestDTO updateDTO : requestDTOs) {
      if(cartList.stream().noneMatch(cart -> cart.getId() == updateDTO.getCartId())){
        throw new BadRequestException("유저의 장바구니에 없는 cartId가 들어왔습니다 : "+updateDTO.getCartId());
      }
    }
  }
  private Option findValidOption(int optionId) {
    return optionRepository.findById(optionId)
      .orElseThrow(() -> new NotFoundException("해당 옵션을 찾을 수 없습니다 : " + optionId));
  }

  private void validateUniqueOptionsInSaveDTO(List<SaveRequestDTO> requestDTOs) {
    if (requestDTOs.size() != requestDTOs.stream().mapToInt(SaveRequestDTO::getOptionId).distinct().count() ) {
      throw new BadRequestException("요청 명세에 동일한 옵션이 2개 이상 존재합니다.");
    }
  }

  // 우발적 중복
  private static void validateUniqueCartsInUpdateDTO(List<UpdateRequestDTO> requestDTOs) {
    if(requestDTOs.size() != requestDTOs.stream().mapToInt(UpdateRequestDTO::getCartId).distinct().count()){
      throw new BadRequestException("요청 명세에 동일한 장바구니 아이디가 2개 이상 존재합니다.");
    }
  }

}
