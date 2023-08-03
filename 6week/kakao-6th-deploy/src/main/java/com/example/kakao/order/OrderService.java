package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user) {


        List<Cart> cartList = cartJPARepository.findByUserIdJoinOptionJoinProduct(user.getId());
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니에 아무 내역도 존재하지 않습니다.");
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);
        cartList.forEach(cart -> {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice()).build();
            itemJPARepository.save(item);
        });

        List<Item> itemList = itemJPARepository.findAllByOrderId(order.getId());
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(order, itemList);
    }

    public OrderResponse.FindByIdDTO findById(int orderId, User user) {

        Order order= orderJPARepository.findByUserIdAndOrderId(user.getId(),orderId ).orElseThrow(
                ()->new Exception404("해당 주문을 찾을 수 없습니다 : "+orderId)
        );

        List<Item> itemList=itemJPARepository.findAllByOrderIdJoinOptionJoinProduct(order.getId());

        return new OrderResponse.FindByIdDTO(order,itemList);
    }
}
