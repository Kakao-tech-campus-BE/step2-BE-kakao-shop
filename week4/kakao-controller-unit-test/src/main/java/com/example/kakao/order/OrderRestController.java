package com.example.kakao.order;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.order.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final FakeStore fakeStore;
    private final OrderService orderService;
    private final GlobalExceptionHandler globalExceptionHandler;

    // ------ Mock API Controller ------
    // (기능12) 결재
    @PostMapping("/orders-mock/save")
    public ResponseEntity<?> saveMock(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders-mock/{id}")
    public ResponseEntity<?> findByIdMock(@PathVariable int id) {
        try {
            Order order = fakeStore.getOrderList().get(id - 1);
            List<Item> itemList = fakeStore.getItemList();
            OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
            return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
        }catch(ArrayIndexOutOfBoundsException e) {
            Exception404 ex = new Exception404("해당 주문을 찾을 수 없습니다:"+id);
            return new ResponseEntity<>(
                    ex.body(),
                    ex.status()
            );
        }
    }

    // ------ Real API Controller ------
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  HttpServletRequest request) {
        try{
            OrderResponse.FindByIdDTO responseDTO = orderService.create(userDetails);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id, HttpServletRequest request) {
        try{
            OrderResponse.FindByIdDTO responseDTO = orderService.getOrderAndItems(id);
            return ResponseEntity.ok(ApiUtils.success(responseDTO));
        }catch (RuntimeException e) {
            return globalExceptionHandler.handle(e, request);
        }
    }
}
