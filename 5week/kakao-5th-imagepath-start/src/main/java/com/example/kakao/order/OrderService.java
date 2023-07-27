package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

  private final CartJPARepository cartJPARepository;
  private final OrderJPARepository orderJPARepository;
  private final ItemJPARepository itemJPARepository;

  @Transactional
  public OrderResponse.FindByIdDTO save(User user) {
    List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());

    if (carts.isEmpty()) {
      throw new Exception400("장바구니가 비어있습니다.");
    }

    Order orders = orderJPARepository.save(Order.builder().user(user).build());
    List<Item> items = new ArrayList<>();

    for (Cart cart : carts) {
      Item item = Item.builder()
          .option(cart.getOption())
          .order(orders)
          .quantity(cart.getQuantity())
          .price(cart.getPrice())
          .build();
      items.add(item);
    }

    List<Item> savedItems = itemJPARepository.saveAll(items);
    cartJPARepository.deleteByUserId(user.getId());

    return new OrderResponse.FindByIdDTO(orders, savedItems);
  }

  @Transactional
  public OrderResponse.FindByIdDTO findById(int id) {
    Order orders = orderJPARepository.findById(id).orElseThrow(
        () -> new Exception400("해당 주문 내역을 찾을 수 없습니다.")
    );
    List<Item> items = itemJPARepository.findByOrderId(id);

    return new OrderResponse.FindByIdDTO(orders, items);
  }
}
