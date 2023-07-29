package com.example.kakao.cart.web;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.domain.service.SaveCartUsecase;
import com.example.kakao.cart.domain.service.SearchCartUseCase;
import com.example.kakao.cart.web.request.CartSaveReqeust;
import com.example.kakao.cart.web.response.CartFindAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartRestController {
    private final SearchCartUseCase searchCartUseCase;
    private final SaveCartUsecase saveCartUsecase;

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<CartFindAllResponse>> findAll(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(ApiUtils.success(searchCartUseCase.execute(user.getUser())));
    }

    @PostMapping
    public ResponseEntity<ApiUtils.ApiResult<CartFindAllResponse>> saveCarts(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody List<CartSaveReqeust> cartSaveRequests) {
        saveCartUsecase.execute(user.getUser(), cartSaveRequests);
        return ResponseEntity.ok(ApiUtils.success());
    }

//    @PutMapping
//    public ResponseEntity<ApiUtils.ApiResult<CartUpdateResponse>> updateCarts(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody List<CartReqeust> cartUpdateRequest) {
//        cartService.updateCarts(user.getUser(), cartUpdateRequest);
//
//        return new ResponseEntity<>(ApiUtils.success(cartUpdateResponse), HttpStatus.OK);
//
//    }
}
