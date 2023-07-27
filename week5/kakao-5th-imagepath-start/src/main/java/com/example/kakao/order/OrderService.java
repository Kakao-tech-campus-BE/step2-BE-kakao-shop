package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveOrder(User sesssionUser) {
        List<Cart> cartList = cartJPARepository.findAll();
        // 장바구니 비어 있을 때 예외 처리

        // 비어있지 않다면
        Order order = Order.builder().user(sesssionUser).build();
        Order orderPS = orderJPARepository.save(order);
        List<Item> itemList = new ArrayList();
        for (Cart cart : cartList) {
            Option optionPS = cart.getOption();
            int quantity = cart.getQuantity();
            int price = cart.getPrice();
            Item item = Item.builder().option(optionPS).order(orderPS).
                    quantity(quantity).price(price).build();
            itemList.add(item);
        }
        List<Item> itemListPS = itemJPARepository.saveAll(itemList);
        cartJPARepository.deleteAll();
        return new OrderResponse.SaveDTO(orderPS, itemListPS);
    }

    @Transactional
    public OrderResponse.findByIdDTO findById(int orderId, User sesssionUser) {
        Optional<Order> orderPS = orderJPARepository.findById(orderId);
        if (orderPS.isPresent()) {
            List<Item> itemListPS = itemJPARepository.mfindByOrderId(orderId);
            return new OrderResponse.findByIdDTO(orderId, itemListPS);
        }
        else {
            throw new Exception400("존재하지 않는 주문 번호");
        }
    }
}
