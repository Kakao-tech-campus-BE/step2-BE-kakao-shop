package com.example.kakaoshop.order.web;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.order.domain.service.OrderService;
import com.example.kakaoshop.order.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
