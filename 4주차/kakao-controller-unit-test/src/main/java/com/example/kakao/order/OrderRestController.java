package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final FakeStore fakeStore;
    private final OrderService orderService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
//        Order order = fakeStore.getOrderList().get(0);
//        List<Item> itemList = fakeStore.getItemList();
        try {
            List<Item> itemList = orderService.orderSave(userDetails);
            OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(itemList.get(0).getOrder(), itemList);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id,HttpServletRequest request) {
//        Order order = fakeStore.getOrderList().get(id-1);
//        List<Item> itemList = fakeStore.getItemList();
        try {
            List<Item> itemList = orderService.orderById(id);
            OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(itemList.get(0).getOrder(), itemList);
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        } catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

}
