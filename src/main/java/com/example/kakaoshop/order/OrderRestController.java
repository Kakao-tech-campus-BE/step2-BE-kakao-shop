package com.example.kakaoshop.order;

import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.CartRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

  @Autowired
  private CartRestController cartRestController; // mock cart clear

  @PostMapping("/order")
  public ResponseEntity<?> order() {

    // TODO: Cart 데이터를 기반으로 주문을 생성한다. -> Order - OrderItem

    // Cart 비우기
    cartRestController.clearMockCartData();

    // TODO: 주문결과를 보여줘야 함.
    return ResponseEntity.ok().body(ApiUtils.success("주문이 완료되었습니다."));
  }

}
