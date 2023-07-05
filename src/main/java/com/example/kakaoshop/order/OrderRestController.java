package com.example.kakaoshop.order;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.cart.CartMockRepository;
import com.example.kakaoshop.cart.response.CartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {

  @Autowired
  private CartMockRepository cartRepository;

  @PostMapping("/order")
  public ResponseEntity<?> order(@AuthenticationPrincipal CustomUserDetails userInfo) {

    // TODO: Cart 데이터를 기반으로 주문을 생성한다. -> Order - OrderItem
    List<CartItemDTO> cartItemDTOList = cartRepository.findAllByUserId(userInfo.getId());

    if(cartItemDTOList.isEmpty()) { // TODO: exception handle
      return ResponseEntity.badRequest().body(ApiUtils.error("장바구니에 상품이 없습니다.", HttpStatus.BAD_REQUEST));
    }

    // 로직을 구현하려면, CartRepository 가 CartItem 을 갖도록 제대로 구현되어야함
//    Product product = cartItemDTOList. ....
//
//    List<OrderItem> orderItems = cartItemDTOList.stream()
//      .map(cartItemDTO -> OrderItem.builder()
//        .productName(cartItemDTO.getName())
//        ....
//
//
//    Order order = Order.builder()
//      .user(null)
//      .orderItems()
//      .build();
//    ....

    // Cart 비우기
    cartRepository.clearMockCartData();

    // TODO: 주문결과를 보여줘야 함.
    return ResponseEntity.ok().body(ApiUtils.success("주문 결과가 들어올 자리."));
  }

  // 주문 내역 목록 조회
  @GetMapping("/orders")
  public ResponseEntity<?> getOrders() {
    return ResponseEntity.ok().body(ApiUtils.success("주문 내역 목록 요약"));
  }

  // 단건 주문 내역 상세 조회
  @GetMapping("/orders/{order-id}")
  public ResponseEntity<?> getOrder(@PathVariable("order-id") int orderId) {
    // TODO: 주문 내역 상세 조회
    return ResponseEntity.ok().body(ApiUtils.success("주문 내역 상세"));
  }

}
