package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

  private final OrderJPARepository orderRepository;
  private final CartJPARepository cartJPARepository;
  private final ItemJPARepository itemRepository;

  @Transactional
  public OrderResponse.FindByIdDTO findById(int id) {

    if (orderRepository.findById(id).isEmpty()) {
      throw new Exception404("해당 주문이 존재하지 않습니다 : " + id);
    }
    // 주문에 해당하는 Item을 가져온다.
    List<Item> items = itemRepository.findByOrderId(id);

    // items의 option에서 product를 추출한다
    return new OrderResponse.FindByIdDTO(id, items);

  }

  @Transactional
  public OrderResponse.SaveDTO save(User user) {

    // cartList가 비었으면, Exception400을 던진다.
    List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
    if (cartList.isEmpty()) {
      throw new Exception400("장바구니가 비어있습니다.");
    }
    Order order = Order.builder().user(user).build();
    orderRepository.save(order);

    List<Item> items = new ArrayList<>();

    for (Cart cart : cartList) {
      Item item = Item.builder()
          .order(order)
          .option(cart.getOption())
          .quantity(cart.getQuantity())
          .price(cart.getPrice())
          .build();
      items.add(item);
    }

    itemRepository.saveAll(items);
    cartJPARepository.deleteAll(cartList);

    return new OrderResponse.SaveDTO(order, items);
  }

}