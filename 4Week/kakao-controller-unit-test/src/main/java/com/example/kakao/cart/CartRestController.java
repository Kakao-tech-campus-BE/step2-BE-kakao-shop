package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;
    private final GlobalExceptionHandler globalExceptionHandler;

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
public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails,
                                     HttpServletRequest request) {
    try {
        requestDTOs.forEach(
                saveDTO -> {
                    System.out.println("요청 받은 장바구니 옵션 : " + saveDTO.toString());
                    cartService.addCart(saveDTO, userDetails);
                });
    }catch (RuntimeException e){
        return globalExceptionHandler.handle(e, request);
    }

    return ResponseEntity.ok(ApiUtils.success(null));
    }

    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     HttpServletRequest request) {
        try {
            CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartService.checkCart(userDetails));
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
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
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails,
                                    HttpServletRequest request) {
        try {
            requestDTOs.forEach(
                    updateDTO -> {
                        System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString());
                        cartService.updateCart(updateDTO);
                    }
            );
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }

        try {
            CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(cartService.checkCart(userDetails));
            // DTO를 만들어서 응답한다.
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
    }

    @PostMapping("/carts/clear")
    public ResponseEntity<?> clear(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        try {
            cartService.deleteCart(userDetails);
        }catch (RuntimeException e){
            return globalExceptionHandler.handle(e, request);
        }
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
