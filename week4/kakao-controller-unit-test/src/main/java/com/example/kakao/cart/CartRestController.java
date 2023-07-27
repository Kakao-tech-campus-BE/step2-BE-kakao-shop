package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final CartService cartService;

    // (기능8) 장바구니 담기
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        // 서비스
        try {
            cartService.addCartList(requestDTOs);
            return ResponseEntity.ok(ApiUtils.success(null));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능9) 장바구니 보기 - (주문화면, 결재화면)
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        // 서비스
        try {
            CartResponse.FindAllDTO responseDTO = cartService.findAll();
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능11) 주문하기 - (장바구니 업데이트)
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
        // 서비스
        try {
            CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
