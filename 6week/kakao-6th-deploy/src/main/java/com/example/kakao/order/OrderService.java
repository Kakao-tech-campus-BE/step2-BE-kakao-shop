package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.UserJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final UserJPARepository userJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    public OrderService(UserJPARepository userJPARepository, OrderJPARepository orderJPARepository, CartJPARepository cartJPARepository, ItemJPARepository itemJPARepository) {
        this.userJPARepository = userJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.itemJPARepository = itemJPARepository;
    }

    @Transactional
    public OrderResponse.SaveDTO save(int userId) {
        List<Cart> carts = cartJPARepository.mFindAllByUserId(userId); // 상품,옵션,카트,유저 페치조인
        if (carts.isEmpty()){throw new Exception400("해당 유저의 장바구니가 비어있습니다.");}
        Order order = Order.builder().user(carts.get(0).getUser()).build();
        orderJPARepository.save(order);
        List<Item> items = new ArrayList<>();
        for (Cart cart : carts) {
            Item item = Item.builder().option(cart.getOption()).quantity(cart.getQuantity()).order(order).price(cart.getPrice()).build();
            items.add(item);
        }
        itemJPARepository.saveAll(items);
        cartJPARepository.deleteAllByUserId(userId); // 주문 후 장바구니 비워주기
        return new OrderResponse.SaveDTO(order, items);
    }

    public OrderResponse.FindByIdDTO findById(int orderId, int userId) {
        List<Item> items = itemJPARepository.mFindAllByOrderId(orderId);
        if (items.isEmpty()) {
            throw new Exception400("해당 주문번호는 없는 번호입니다.");
        }
        if (items.get(0).getOrder().getUser().getId() != userId) {
            throw new Exception400("요청한 유저의 주문이 아닙니다.");
        }
        return new OrderResponse.FindByIdDTO(items.get(0).getOrder(), items);

    }


}


