package com.example.kakao.cart.web;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.domain.service.SearchCartService;
import com.example.kakao.cart.web.response.CartFindAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartRestController {
    private final SearchCartService searchCartService;

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<CartFindAllResponse>> findAll(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(ApiUtils.success(searchCartService.getCartsByUser(user.getUser())));
    }

//    @PostMapping
//    public ResponseEntity<ApiUtils.ApiResult> saveCarts(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody List<CartReqeust>cartSaveRequests) {
//        cartService.addCarts(user.getUser(), cartSaveRequests);
//        return ResponseEntity.ok(ApiUtils.successWithoutData());
//    }

//    @PutMapping
//    public ResponseEntity<ApiUtils.ApiResult<CartUpdateResponse>> updateCarts(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody List<CartReqeust> cartUpdateRequest) {
//        cartService.updateCarts(user.getUser(), cartUpdateRequest);
//
//        return new ResponseEntity<>(ApiUtils.success(cartUpdateResponse), HttpStatus.OK);
//
//    }
}
