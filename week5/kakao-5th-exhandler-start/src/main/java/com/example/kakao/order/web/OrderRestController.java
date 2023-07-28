package com.example.kakao.order.web;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.domain.service.OrderService;
import com.example.kakao.order.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderRestController {
        private final OrderService orderService;

        @PostMapping
        public ResponseEntity<ApiUtils.ApiResult<OrderResponse>> createOrder(@AuthenticationPrincipal CustomUserDetails user) {
            return ResponseEntity.ok(ApiUtils.success(orderService.saveOrder(user.getUser())));
        }
}
