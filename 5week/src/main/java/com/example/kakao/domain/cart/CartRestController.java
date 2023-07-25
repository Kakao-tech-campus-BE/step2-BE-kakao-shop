package com.example.kakao.domain.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiResponse;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.domain.cart.dto.*;
import com.example.kakao.domain.cart.dto.request.SaveRequestDTO;
import com.example.kakao.domain.cart.dto.request.UpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartRestController {

  private final CartService cartListService;

  /**
   * [
   * {
   * "optionId":1,
   * "quantity":5
   * },
   * {
   * "optionId":2,
   * "quantity":5
   * }
   * ]
   */
  // (기능6) 장바구니 담기 POST
  @PostMapping("/carts/add")
  public ResponseEntity<ApiResponse> addCartList(
    @RequestBody @Valid List<SaveRequestDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
    cartListService.addCartList(requestDTOs, userDetails.getUser());
    return ResponseEntity.ok(ApiUtils.success());
  }

  // (기능7) 장바구니 조회 - (주문화면) GET
  @GetMapping("/carts")
  public ResponseEntity<ApiResponse> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(ApiUtils.success(
      cartListService.findAll(userDetails.getUser()))
    );
  }


  /**
   * [
   * {
   * "cartId":1,
   * "quantity":10
   * },
   * {
   * "cartId":2,
   * "quantity":10
   * }
   * ]
   */
// (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
  @PostMapping("/carts/update")
  public ResponseEntity<ApiResponse> update(
    @RequestBody @Valid List<UpdateRequestDTO> requestDTOs,
    @AuthenticationPrincipal CustomUserDetails userDetails) {

    return ResponseEntity.ok(ApiUtils.success(
      cartListService.update(requestDTOs, userDetails.getUser()))
    );
  }
}
