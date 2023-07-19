package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;
    
// [
//     {
//         "optionId":1,
//         "quantity":5
//     },
//     {
//         "optionId":2,
//         "quantity":5
//     }
// ]
    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDTOs.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.findAllCartItems();
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }


// [
//     {
//         "cartId":1,
//         "quantity":10
//     },
//     {
//         "cartId":2,
//         "quantity":10
//     }
// ]
    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );

        CartResponse.UpdateDTO responseDTO = cartService.updateCartItems(requestDTOs);

        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

// [
//  {
//      "cartId":1,
//  },
//  {
//      "cartId":2,
//  }
//]
    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	cartService.clearCart();
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
