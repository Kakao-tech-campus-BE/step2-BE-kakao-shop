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
    @PostMapping("/carts/add")
    public ResponseEntity<?> addCart(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.addCart(requestDTOs, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }


    //  (기능7) 장바구니 조회 - (주문화면) GET
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.FindAllDTO responseDTO = cartService.findAll(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
//    @GetMapping("/carts/v2")
//    public ResponseEntity<?> findAllv2(@AuthenticationPrincipal CustomUserDetails userDetails) {    // 세션 값 가져오기
//        CartResponse.FindAllDTOv2 responseDTO = cartService.findAllv2(userDetails.getUser());
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }


    // (기능8) 주문하기 - (주문화면에서 장바구니 수정하기)
    @PostMapping("/carts/update")
    public ResponseEntity<?> updateCart(@RequestBody @Valid List<CartRequest.UpdateDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse.UpdateDTO responseDTO = cartService.updateCart(requestDTOs,userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
