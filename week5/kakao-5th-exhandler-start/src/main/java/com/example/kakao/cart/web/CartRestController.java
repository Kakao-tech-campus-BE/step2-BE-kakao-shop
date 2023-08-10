package com.example.kakao.cart.web;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiResult;
import com.example.kakao.cart.domain.service.SaveCartUsecase;
import com.example.kakao.cart.domain.service.SearchCartUseCase;
import com.example.kakao.cart.domain.service.UpdateCartUseCase;
import com.example.kakao.cart.web.request.CartSaveReqeust;
import com.example.kakao.cart.web.request.CartUpdateRequest;
import com.example.kakao.cart.web.response.CartFindAllResponse;
import com.example.kakao.cart.web.response.CartUpdateResponse;
import lombok.RequiredArgsConstructor;
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
    private final UpdateCartUseCase updateCartUseCase;

    @GetMapping
    public ApiResult<CartFindAllResponse> findAll(@AuthenticationPrincipal CustomUserDetails user) {
        return ApiResult.success(searchCartUseCase.execute(user.getUser()));
    }

    @PostMapping
    public ApiResult<CartFindAllResponse> saveCarts(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody List<CartSaveReqeust> cartSaveRequests) {
        saveCartUsecase.execute(user.getUser(), cartSaveRequests);
        return ApiResult.success();
    }

    @PutMapping
    public ApiResult<CartUpdateResponse> updateCarts(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody List<CartUpdateRequest> requests) {
        return ApiResult.success(updateCartUseCase.execute(user.getUser(), requests));
    }
}
