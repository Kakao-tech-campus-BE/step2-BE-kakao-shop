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

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO orderSave(User user) {
        List<Cart> cartListPS = cartJPARepository.findAllByUserId(user.getId());

        // 유저 장바구니에 아무것도 없으면 예외처리
        if (cartListPS.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다");
        }

        // 주문 생성
        Order order = Order.builder().user(user).build();
        Order orderPS = orderJPARepository.save(order);

        for (Cart cartPS : cartListPS) {
            // 주문아이템 생성
            Item item = Item.builder().order(order)
                    .option(cartPS.getOption())
                    .quantity(cartPS.getQuantity())
                    .price(cartPS.getPrice())
                    .build();
            itemJPARepository.save(item);

            // 장바구니 비우기
            cartJPARepository.deleteById(cartPS.getId());
        }

        List<Item> itemListPS = itemJPARepository.findByOrderId(orderPS.getId());
        return new OrderResponse.SaveDTO(itemListPS);
    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id) { // User 추가로 변경 - 다른 애는 못보도록
        Order orderPS = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다 : "+id)
        );
        List<Item> itemListPS = itemJPARepository.findByOrderId(orderPS.getId());
        return new OrderResponse.FindByIdDTO(itemListPS);
    }
}
