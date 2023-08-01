package com.example.kakao.domain.order;

import com.example.kakao._core.errors.exception.NotFoundException;
import com.example.kakao._core.errors.exception.UnAuthorizedException;
import com.example.kakao.domain.cart.Cart;
import com.example.kakao.domain.cart.CartJPARepository;
import com.example.kakao.domain.cart.collection.CartList;
import com.example.kakao.domain.order.dto.OrderDetailResponseDTO;
import com.example.kakao.domain.order.item.Item;
import com.example.kakao.domain.order.item.ItemJPARepository;
import com.example.kakao.domain.user.User;
import com.example.kakao.domain.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
  private final UserJPARepository userRepository;
  private final CartJPARepository cartRepository;
  private final OrderJPARepository orderRepository;
  private final ItemJPARepository itemRepository;

  @Transactional
  public OrderDetailResponseDTO save(int userId) {
    User user = findValidUserById(userId);
    CartList cartList = new CartList( cartRepository.findAllByUserId(userId) );

    Order order = new Order(user);

    List<Item> items = buildOrderItems(order, cartList.getItems());

    orderRepository.save(order);
    itemRepository.saveAll(items);

    cartRepository.deleteAllByUserId(userId); // 장바구니 초기화

    return new OrderDetailResponseDTO(order, items);
  }

  public OrderDetailResponseDTO findById(int orderId, int userId) {
    Order order = findValidOrderById(orderId);

    validateOrderAccessPermission(userId, order);

    List<Item> items = itemRepository.findAllByOrderId(order.getId());
    return new OrderDetailResponseDTO(order, items);
  }

  private List<Item> buildOrderItems(Order order, List<Cart> cartList) {
    return cartList.stream().map(cart -> Item.builder()
      .order(order)
      .option(cart.getOption())
      .quantity(cart.getQuantity())
      .price( cart.getPrice() ) // cart 가 생성될 때, 이미 total Price 가 계산되어있음.
      .build())
      .collect(Collectors.toList());
  }


  private User findValidUserById(int userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다. : " + userId));
  }

  private Order findValidOrderById(int id) {
    return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("주문 내역을 찾을 수 없습니다. : " + id));
  }
  private void validateOrderAccessPermission(int userId, Order order) {
    if(order.getUser().getId() != userId) throw new UnAuthorizedException("주문내역을 확인할 권한이 없습니다.");
  }

}
