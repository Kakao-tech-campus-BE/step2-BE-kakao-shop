package com.example.kakao.order.web;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiResult;
import com.example.kakao.order.domain.service.FindOrderUsecase;
import com.example.kakao.order.domain.service.SaveOrderUseCase;
import com.example.kakao.order.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderRestController {
    private final SaveOrderUseCase saveOrderUseCase;
    private final FindOrderUsecase findOrderUsecase;

    @PostMapping("/save")
    public ApiResult<OrderResponse> createOrder(@AuthenticationPrincipal CustomUserDetails user) {
        return ApiResult.success(saveOrderUseCase.execute(user.getUser()));
    }

    @GetMapping("/{id}")
    public ApiResult<OrderResponse> findById(@PathVariable Long id) {
        return ApiResult.success(findOrderUsecase.execute(id));
    }
}
