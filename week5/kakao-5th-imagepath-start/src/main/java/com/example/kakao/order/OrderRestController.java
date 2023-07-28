package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {
    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    private final OrderService orderService;
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindByIdDTO responseDTO = orderService.save(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);

        return ResponseEntity.ok(apiResult);
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> orderFindById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails){
        OrderResponse.FindByIdDTO responseDTO = orderService.findOrderById(id,userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);

        return ResponseEntity.ok(apiResult);
    }
}
