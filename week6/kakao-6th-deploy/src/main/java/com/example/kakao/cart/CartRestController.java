package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.CartRequest.SaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
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
     *     {
     *         "optionId":1,
     *         "quantity":5
     *     },
     *     {
     *         "optionId":2,
     *         "quantity":5
     *     }
     * ]
     */
    // (기능6) 장바구니 담기 POST
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(
            @RequestBody @Valid List<SaveDTO> requestDTOs, Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Service Layer 호출 -> addCartList 수행
        cartListService.addCartList(requestDTOs, userDetails.getUser());
        // DTO 생성 및 반환
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // (기능7) 장바구니 조회 - (주문화면) GET
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // Service Layer 호출 -> findAll 수행
        CartResponse.FindAllDTO responseDTOs = cartListService.findAll(userDetails.getUser().getId());
        // DTO 생성 및 반환
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }


    /**
     *  [
     *      {
     *          "cartId":1,
     *          "quantity":10
     *      },
     *      {
     *          "cartId":2,
     *          "quantity":10
     *      }
     *  ]
     */
    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Service Layer 호출 -> updateCart 수행
        CartResponse.UpdateDTO responseDTO = cartListService.update(requestDTOs, userDetails.getUser());
        // DTO 생성 및 반환
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}
