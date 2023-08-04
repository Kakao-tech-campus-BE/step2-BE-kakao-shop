package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.SaveDTO orderSave(User userSession){

        if(cartJPARepository.findAllByUserId(userSession.getId()).isEmpty()){
            throw new Exception400("장바구니가 비어있습니다");
        }

        Order order = new Order().builder()
                .user(userSession)
                .build();
        orderJPARepository.save(order);
        List<Cart> cartList = cartJPARepository.findAllByUserId(userSession.getId());

        for(Cart cart : cartList){
            Item item = Item.builder()
                    .order(order)
                    .price(cart.getPrice())
                    .quantity(cart.getQuantity())
                    .option(cart.getOption())
                    .build();
            itemJPARepository.save(item);
        }

        cartJPARepository.deleteByUserId(userSession.getId());

        List<Item> items = itemJPARepository.findAllByOrderId(order.getId());
        OrderResponse.SaveDTO responseDTO  = new OrderResponse.SaveDTO(items);
        return responseDTO;
    }

    public OrderResponse.FindByIdDTO findById(User userSession,int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                ()->new Exception400("존재하지 않는 주문ID입니다")
        );

        if(order.getUser().getId() != userSession.getId()){
            throw new Exception401("해당 주문을 열람할 권한이 없습니다");
        }

        List<Item> items = itemJPARepository.findByOrderId(order.getId());

        return new OrderResponse.FindByIdDTO(items);



    }
}
