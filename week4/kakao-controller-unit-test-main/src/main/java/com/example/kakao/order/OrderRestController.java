package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao._core.utils.FakeStore;
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

    private final FakeStore fakeStore;

    // (기능12) 결재
    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Order order = fakeStore.getOrderList().get(0);
        List<Item> itemList = fakeStore.getItemList();
        if (itemList.size() == 0){
            Exception400 e = new Exception400("주문 가능한 물품이 1개 이상이어야 합니다.");
            return new ResponseEntity<>(
                    e.body(),
                    e.status()
            );
        }
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // (기능13) 주문 결과 확인
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Order order = fakeStore.getOrderList().get(id-1);
        List<Item> itemList = fakeStore.getItemList();
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }

}
