package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ErrorLogJPARepository errorLogJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(@AuthenticationPrincipal CustomUserDetails userDetails){
        try{
            Order order = Order.builder()
                    .user(userDetails.getUser())
                    .build();
            orderJPARepository.save(order);
            List<Cart> cartList = cartJPARepository.findByUserId(userDetails.getUser().getId());
            for (Cart cart : cartList) {
                Item item = Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build();
                itemJPARepository.save(item);
            }

            Order findOrder = orderJPARepository.findById(order.getId()).orElseThrow(
                    () -> new Exception400("옵션을 찾을 수 없습니다. : " + order.getId())
            );
            List<Item> itemList = itemJPARepository.mFindByOrderId(findOrder.getId());
            return new OrderResponse.FindByIdDTO(order, itemList);
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }

    public OrderResponse.FindByIdDTO findById(int id){
        try{
            Order order = orderJPARepository.findById(id).orElseThrow(
                    () -> new Exception400("옵션을 찾을 수 없습니다. : " + id)
            );
            List<Item> itemList = itemJPARepository.mFindByOrderId(order.getId());
            return new OrderResponse.FindByIdDTO(order, itemList);
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }
}
