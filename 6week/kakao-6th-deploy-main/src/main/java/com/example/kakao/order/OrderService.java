package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
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

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.SaveDTO save(User user){
        // 유저 장바구니 불러와서 주문 저장하기
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        if(cartList.isEmpty()){
            throw new Exception404("주문할 수 있는 상품이 없습니다");
        }
        Order order = orderJPARepository.save(Order.builder().user(user).build());
        for (Cart cart : cartList){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .price(cart.getPrice())
                    .quantity(cart.getQuantity()).build();
            itemJPARepository.save(item);
        }
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse.SaveDTO(order, cartList);
    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(int id, User user){
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 주문을 찾을 수 없습니다: " + id)
        );
        if(order.getUser().getId() != user.getId()){
            throw new Exception403("잘못된 주문 번호입니다: " + id);
        }
        List<Item> itemList = itemJPARepository.findAllByOrder(id);
        return new OrderResponse.FindByIdDTO(order, itemList);
    }
}
