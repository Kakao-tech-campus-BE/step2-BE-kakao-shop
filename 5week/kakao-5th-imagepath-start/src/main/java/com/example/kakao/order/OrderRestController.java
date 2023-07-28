package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class OrderRestController {
    private final OrderService orderService;
    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.SaveDTO responseDTO = orderService.saveOrder(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

//    public ResponseEntity<?> addCartList(@RequestBody @Valid List<CartRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        cartListService.addCartList(requestDTOs, userDetails.getUser());
//        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
//        return ResponseEntity.ok(apiResult);
//    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?>findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.findByIdDTO responseDTO = orderService.findById(id, userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);

        
    }
}
