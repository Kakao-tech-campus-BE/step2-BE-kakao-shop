package com.example.kakao.domain.cart.service;

import com.example.kakao._core.errors.exception.NotFoundException;
import com.example.kakao.domain.cart.Cart;
import com.example.kakao.domain.cart.CartJPARepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
  private final CartJPARepository cartRepository;
  private final OptionJPARepository optionRepository;

  private final CartRequestValidationService cartRequestValidationService;

  @Transactional
  public void addCartList(List<SaveRequestDTO> requestDTOs, User sessionUser) {
    cartRequestValidationService.validateUniqueOptionsInSaveDTO(requestDTOs); // Dto 에 동일한 옵션이 2개 이상 존재하면 예외처리

    for (SaveRequestDTO requestDTO : requestDTOs) {
      int optionId = requestDTO.getOptionId();
      int quantity = requestDTO.getQuantity();
      Option option = findValidOptionById(optionId);

      Optional<Cart> existingCartItem = cartRepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
      // 이미 장바구니에 존재하면 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
      if (existingCartItem.isPresent()) {
        updateExistingCartItem(quantity, option, existingCartItem.get());
      } else {
        createNewCartItem(sessionUser, quantity, option);
      }
    }
  }


  // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다. -> 투박하지만 짧고 효과적인 설명.
  public FindAllResponseDTO findAll(User user) {
    List<Cart> cartList = cartRepository.findAllByUserIdOrderByOptionIdAsc(user.getId());
    return new FindAllResponseDTO(cartList);
  }


  @Transactional
  public UpdateResponseDTO update(List<UpdateRequestDTO> requestDTOs, User user) {
    cartRequestValidationService.validateUniqueCartsInUpdateDTO(requestDTOs); // 요청 명세에 중복 값이 없는가

    CartList cartList = new CartList( cartRepository.findAllByUserId(user.getId()) );

    updateCartItemInRequest(requestDTOs, cartList);

    return new UpdateResponseDTO(cartList.getItems());
  } // 더티체킹



  // ----------------- private -----------------

  /**
   * 요청에 들어있는 아이템만 수정한다.
   */
  private void updateCartItemInRequest(List<UpdateRequestDTO> requestDTOs, CartList cartList) {
    requestDTOs.forEach(updateDTO -> {
      Cart cartInRequest = cartList.findCartInRequest(updateDTO);

      updateOrDeleteCartItem(updateDTO, cartInRequest, cartList);
    });
  }

  /**
   * 요청에 수량이 0이면 삭제, 아니면 해당 값으로 덮어쓴다.
   */
  private void updateOrDeleteCartItem(UpdateRequestDTO updateDTO, Cart cartInRequest, CartList cartList) {
    if(isDeleteRequest(updateDTO)) {
      cartRepository.delete(cartInRequest); // DB에서 삭제
      cartList.remove(cartInRequest); // 응답 Dto 에서 삭제되기 위해
    } else {
      cartInRequest.update(updateDTO.getQuantity(), calcTotalPrice(cartInRequest.getOption(), updateDTO.getQuantity()));
    }
  }

  private Option findValidOptionById(int optionId) {
    return optionRepository.findById(optionId)
      .orElseThrow(() -> new NotFoundException("해당 옵션을 찾을 수 없습니다 : " + optionId));
  }

  private boolean isDeleteRequest(UpdateRequestDTO updateDTO) {
    return updateDTO.getQuantity() == 0;
  }


  private void createNewCartItem(User sessionUser, int quantity, Option option) {
    Cart cart = Cart.builder()
      .user(sessionUser)
      .option(option)
      .quantity(quantity)
      .price( calcTotalPrice(option, quantity) )
      .build();
    cartRepository.save(cart);
  }

  private void updateExistingCartItem(int quantity, Option option, Cart cart) {
    cart.update(
      cart.getQuantity() + quantity,
      calcTotalPrice(option, quantity, cart.getPrice() ));
  }

  private long calcTotalPrice(Option option, int quantity, long... additionalPrice) {
    long totalPrice = option.getPrice() * quantity + Arrays.stream(additionalPrice).sum();
    cartRequestValidationService.validatePriceOverflow(totalPrice);
    return totalPrice;
  }

}
