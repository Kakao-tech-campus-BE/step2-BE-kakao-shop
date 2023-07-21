package com.example.kakao.order;

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
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("주문 내역을 찾을 수 없습니다:"+id));
        List<Item> itemList = itemJPARepository.findByOrder(order);
        OrderResponse.FindByIdDTO findByIdDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return findByIdDTO;
    }

    @Transactional
    public OrderResponse.FindByIdDTO save() {
        User user = new User(1, "ssar@nate.com", null, null, "USER");
        Order order = new Order(1, user);
        order = orderJPARepository.save(order);
        List<Cart> carts = cartJPARepository.findByUser(user);
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : carts) {
            Item item = new Item(cart.getId(), cart.getOption(), order, cart.getQuantity(), cart.getPrice());
            itemList.add(item);
        }
        OrderResponse.FindByIdDTO findByIdDTO = new OrderResponse.FindByIdDTO(order, itemList);
        return findByIdDTO;
    }
}
