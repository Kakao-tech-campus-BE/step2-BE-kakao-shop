package com.example.kakaoshop.domain.order;

import com.example.kakaoshop._core.security.CustomUserDetails;
import com.example.kakaoshop._core.utils.ApiUtils;
import com.example.kakaoshop.domain.cart.CartItem;
import com.example.kakaoshop.domain.cart.CartMockRepository;
import com.example.kakaoshop.domain.order.item.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 전체적으로 Mock Data 로는 테스트하기가 너무 까다로운것 같아서
 * 구현부를 비워두었습니다.
 */
@RestController
@RequiredArgsConstructor
public class OrderRestController {

  private final CartMockRepository cartRepository;
  private final OrderMockRepository orderRepository;
  private final OrderItemMockRepository orderItemRepository;

  // TODO: Transactional
  @PostMapping("/order")
  public ResponseEntity<Object> order(@AuthenticationPrincipal CustomUserDetails userInfo) {

    List<CartItem> cartItemList = cartRepository.findAllByUserId(userInfo.getId());

    if(cartItemList.isEmpty()) { // TODO: exception handle
      return ResponseEntity.badRequest().body(ApiUtils.error("장바구니에 상품이 없습니다.", HttpStatus.BAD_REQUEST));
    }

    // 1. 주문 생성
    Order order = Order.builder()
      .account(userInfo.getAccount())
      .build();
    orderRepository.save(order);

    // 2. 주문 상품 생성
    List<OrderItem> orderItemList = cartItemList.stream()
      .map(cartItem -> OrderItem.builder()
        .order(order)
        .productName(cartItem.getProductOption().getProduct().getProductName())
        .optionName(cartItem.getProductOption().getName())
        .price(cartItem.getProductOption().getPrice())
        .quantity(cartItem.getQuantity())
        .build())
      .collect(Collectors.toList());
    orderItemRepository.saveAll(orderItemList);

    // 3. 주문 연결
    order.setOrderItems(orderItemList);
    // Cart 비우기
    cartRepository.clearMockCartData();

    // TODO: 주문결과를 보여줘야 함.
    return ResponseEntity.ok().body(ApiUtils.success("주문 결과가 들어올 자리."));
  }

  // 주문 내역 목록 조회
  @GetMapping("/orders")
  public ResponseEntity<Object> getOrders() {
    // TODO: 주문 내역 목록 조회
    return ResponseEntity.ok().body(ApiUtils.success("주문 내역 목록 요약"));
  }

  // 단건 주문 내역 상세 조회
  @GetMapping("/orders/{order-id}")
  public ResponseEntity<Object> getOrder(@PathVariable("order-id") int orderId) {
    // TODO: 주문 내역 상세 조회
    return ResponseEntity.ok().body(ApiUtils.success("주문 내역 상세"));
  }

}
