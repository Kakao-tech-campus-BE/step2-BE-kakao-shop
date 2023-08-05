package com.example.kakao.order;

import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;


    // (기능9) 결재하기 - (주문 인서트) POST
    // /orders/save
    @PostMapping("/order")
    public ResponseEntity<?> save(@RequestBody @Valid List<OrderRequest.SaveDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.save(requestDTOs, userDetails);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // (기능10) 주문 결과 확인 GET
    // /orders/{id}
    @GetMapping("/order/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        OrderResponse.FindById responseDTO = orderService.findById(id, userDetails);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

}
