package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartService;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO create(CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        if(cartList.isEmpty()) throw new Exception404("주문할 데이터가 없습니다.");

        Order order = Order.builder()
                .user(user)
                .build();
        try {
            orderJPARepository.save(order);
        } catch (Exception e) {
            throw new Exception500("주문 저장 과정에서 오류가 발생했습니다." + e.getMessage());
        }

        List<Item> itemList = cartList.stream()
                .map(c -> Item.builder()
                        .order(order)
                        .option(c.getOption())
                        .quantity(c.getQuantity())
                        .price(c.getPrice())
                        .build())
                .collect(Collectors.toList());

        try {
            itemJPARepository.saveAll(itemList);
        } catch (Exception e) {
            throw new Exception500("주문상품 저장 과정에서 오류가 발생했습니다." + e.getMessage());
        }

        try {
            cartJPARepository.deleteAllInBatch(cartList);
        } catch (Exception e) {
            throw new Exception500("장바구니 비우기 과정에서 오류가 발생했습니다." + e.getMessage());
        }

        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    public OrderResponse.FindByIdDTO getOrderAndItems(int orderId) {
        // 이렇게 각각 나눠서 조회하기 vs 1번의 쿼리로 구하기
        Order order = orderFindById(orderId);
        List<Item> itemList = itemFindByOrderId(orderId);
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    private Order orderFindById(int orderId) {
        return orderJPARepository.findById(orderId).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다:"+orderId));
    }

    private List<Item> itemFindByOrderId(int orderId) {
        return itemJPARepository.findByOrderId(orderId);
    }
}
