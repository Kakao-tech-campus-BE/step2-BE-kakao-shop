package com.example.kakao.order;

import com.example.kakao._core.errors.ErrorCode;
import com.example.kakao._core.errors.exception.CustomException;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        // 1. 장바구니가 비어있는가 체크
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());
        if (carts.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_CART);
        }

        // 2. 주문 생성
        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);

        // 3. 장바구니 아이템을 orderItem으로 옮기기
        List<Item> items = carts.stream()
                .map(cart -> Item.builder()
                        .order(order)
                        .option(cart.getOption())
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());

        itemJPARepository.saveAll(items);

        // 4. 장바구니 삭제
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(items);
    }

    @Transactional(readOnly = true)
    public OrderResponse.FindByIdDTO findById(int id) {

        // 1. 존재하는 id인가 체크
        List<Item> items = itemJPARepository.findAllByOrderId(id);
        if (items.isEmpty()) {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return new OrderResponse.FindByIdDTO(items);
    }
}
