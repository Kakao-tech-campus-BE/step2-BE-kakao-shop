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

import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {
    private final OrderService orderService;


    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.SaveDTO saveDTO = orderService.save(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(saveDTO);
        return ResponseEntity.ok(apiResult);
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable @Min(1) int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.SaveDTO saveDTO = orderService.findOrderById(id, userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(saveDTO);
        return ResponseEntity.ok(apiResult);
    }

}
