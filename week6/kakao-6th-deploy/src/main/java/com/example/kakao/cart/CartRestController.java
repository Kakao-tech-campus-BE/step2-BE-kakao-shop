package com.example.kakao.cart;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
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

    private final CartService cartService;

    // (기능6) 장바구니 담기 POST
    // /carts/add
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCartList(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success( null));
    }

    // (기능7) 장바구니 조회 - (주문화면) GET
    // /carts
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    // /carts/updaate
    @PostMapping("/carts/update")
    public ResponseEntity<?> update(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.UpdateDTO responseDTO = cartService.update(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

}