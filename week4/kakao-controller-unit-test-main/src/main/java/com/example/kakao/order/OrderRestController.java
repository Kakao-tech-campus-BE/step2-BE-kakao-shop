package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderRestController {
    private final GlobalExceptionHandler globalExceptionHandler;

    private final FakeStore fakeStore;

    // (기능12) 결재
    @PostMapping("/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (fakeStore.getOrderList().isEmpty()) {
            return globalExceptionHandler.getApiErrorResultResponseEntity(new Exception400("주문목록이 비어있습니다."));
        }
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        if (id < 1) {
            return globalExceptionHandler.getApiErrorResultResponseEntity(new Exception403("잘못된 요청입니다."));
        } else {
            try {
                Order order = fakeStore.getOrderList().get(id - 1);
                List<Item> itemList = fakeStore.getItemList();
                OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
                return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
            } catch (Exception e) {
                return globalExceptionHandler.getApiErrorResultResponseEntity(new Exception404("주문을 찾을 수 없습니다."));
            }
        }
    }
}
