package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO saveOrder(User user) {
        List<Cart> carts = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        if (carts.isEmpty()) throw new Exception404("장바구니에서 주문할 상품이 없습니다.");

        Order order = Order.builder().user(user).build();
        try { orderJPARepository.saveAndFlush(order); } catch (Exception e) {
            throw new Exception500("주문을 저장하는 과정에서 오류가 발생했습니다");
        }

        List<Item> items = carts.stream()
                .map(cart -> Item.builder()
                        .order(order)
                        .option(cart.getOption())
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build()).collect(Collectors.toList());

        try { itemJPARepository.saveAllAndFlush(items); } catch (Exception e) {
            throw new Exception500("주문 상품을 저장하는 과정에서 오류가 발생했습니다.");
        }
        try { cartJPARepository.deleteByUserId(user.getId()); } catch (Exception e) {
            throw new Exception500("주문한 장바구니를 비우는 과정에서 오류가 발생했습니다.");
        }
        return new OrderResponse.SaveDTO(order, items);
    }

    public OrderResponse.FindByIdDTO findOrderById(User user, int id) {

        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception404("해당 주문을 찾을 수 없습니다."));

        if (order.getUser().getId() != user.getId()) throw new Exception403("주문에 접근 권한이 없습니다.");

        List<Item> items = itemJPARepository.findByOrderId(id);
        if (items.isEmpty()) throw new Exception404("해당 주문의 상품을 찾을 수 없습니다.");

        return new OrderResponse.FindByIdDTO(order, items);
    }
}
