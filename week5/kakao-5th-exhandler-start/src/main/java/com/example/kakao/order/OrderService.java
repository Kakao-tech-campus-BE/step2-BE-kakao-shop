package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    @Transactional
    public OrderResponse.SaveDTO saveOrder(User ssesionUser) {
        List<Cart> findCart = cartJPARepository.findAllByUserId(ssesionUser.getId());
        if(findCart.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다.");
        }
        Order order = Order.builder().user(ssesionUser).build();
        orderJPARepository.save(order);
        for (Cart cart : findCart) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice()).build();
            itemJPARepository.save(item);
        }
        cartJPARepository.deleteByUserId(ssesionUser.getId());

        return new OrderResponse.SaveDTO(order, findCart);
    }

    public OrderResponse.FindByIdDTO findById(int id, User ssesionUser) {
        Optional<Order> order = orderJPARepository.findById(id);
        List<Item> itemList = itemJPARepository.findAllByOrderId(id);
        if(order.isEmpty()){
            throw new Exception404("올바르지 않은 주문번호입니다.");
        }
        if(order.get().getUser().getId() != ssesionUser.getId()){
            throw new Exception401("자신의 주문결과만 볼 수 있습니다.");
        }
        return new OrderResponse.FindByIdDTO(order.get(), itemList);
    }

}
