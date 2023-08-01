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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;





    public OrderResponse.saveDTO save(User sessionUser) {
        List<Cart> carts = cartJPARepository.mFindAllByUserId(sessionUser.getId());

        Order order = Order.builder()
                .user(sessionUser)
                .build();
        orderJPARepository.save(order);

        List<Item> items = carts.stream().map(
                x -> Item.builder()
                        .price(x.getPrice())
                        .quantity(x.getQuantity())
                        .order(order)
                        .option(x.getOption())
                        .build()
        ).collect(Collectors.toList());;

        itemJPARepository.saveAll(items);


        cartJPARepository.deleteByUserId(sessionUser.getId());
        return OrderResponse.saveDTO.builder()
                .id(order.getId())
                .items(items)
                .build();
    }


    public OrderResponse.findAllDTO findAll (int id){
        List<Item> items = itemJPARepository.findByOrderId(id);
        if(items==null || items.isEmpty()) {
            throw new Exception404("해당된 아이템을 찾을 수 없습니다.");
        }
        return OrderResponse.findAllDTO
                .builder()
                .id(id)
                .items(items)
                .build();
    }
}
