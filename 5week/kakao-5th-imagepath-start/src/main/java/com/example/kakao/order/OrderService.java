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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    //주문 저장
    @Transactional
    public OrderResponse.SaveDTO saveOrder(User sesssionUser){
        List<Cart> carts = cartJPARepository.findAll();
        //장바구니가 비었을때
        if (carts.isEmpty()){
            throw new Exception400("장바구니가 비어있습니다.");
        }
        else {
            Order order = Order.builder().user(sesssionUser).build();
            Order orderPS = orderJPARepository.save(order);
            List<Item> items = new ArrayList();
            for (Cart cart : carts){
                Option optionPS = cart.getOption();
                int quantity = cart.getQuantity();
                int price = cart.getPrice();
                Item item= Item.builder().option(optionPS).order(order).quantity(quantity).price(price).build();
                items.add(item);
            }
            List<Item> itemsPS=itemJPARepository.saveAll(items);
            cartJPARepository.deleteAll();
            return new OrderResponse.SaveDTO(orderPS,itemsPS);
        }
    }

    //id찾기
    @Transactional
    public OrderResponse.findByIdDTO findById(int orderId, User sessionUser){
        Optional<Order> orderPS = orderJPARepository.findById(orderId);
        if (orderPS.isPresent()){
            List<Item> itemsPS = itemJPARepository.mfindByOrderId(orderId);
            return new OrderResponse.findByIdDTO(orderPS,itemsPS);
        }
        else{
            throw new Exception400("주문번호가 존재하지 않습니다");
        }
    }
}
