package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;

    public OrderResponse.FindAllDTO findById(int id){
        List<Item> itemList = itemJPARepository.findAllByOrderId(id);

        // item 아무것도 없으면 예외처리
        if(itemList.size() == 0){
            throw new Exception404("해당 주문을 찾을 수 없음 : "+id);
        }

        return new OrderResponse.FindAllDTO(itemList);
    }
    @Transactional
    public void addOrder(User sessionUser){
        List<Cart> cartList = cartJPARepository.findAll();

        // 장바구니에 아무것도 없으면 예외처리
        if(cartList.isEmpty()){
            throw new Exception404("장바구니가 텅 비어있음");
        }

        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order);

        for(Cart cart : cartList){
            Option option = cart.getOption();
            int quantity = cart.getQuantity();
            int price = cart.getPrice();

            Item item = Item.builder().option(option).order(order).quantity(quantity).price(price).build();
            itemJPARepository.save(item);
        }
    }

    @Transactional
    public void clearOrder() {
        itemJPARepository.deleteAll();
        orderJPARepository.deleteAll();
    }
}
