package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    // (기능9) 결재하기 - (주문 인서트) POST
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.OrderInsertDTO responseDTO = orderService.save(userDetails.getUser());
        // DTO 생성 및 반환
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // (기능10) 주문 결과 확인 GET
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.OrderCheckDTO responseDTO = orderService.orderCheck(id, userDetails.getUser().getId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}
