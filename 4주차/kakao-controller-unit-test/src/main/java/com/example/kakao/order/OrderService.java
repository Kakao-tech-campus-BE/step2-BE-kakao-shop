package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartJPARepository cartJPARepository;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    public List<Item> orderSave(@AuthenticationPrincipal CustomUserDetails userDetails) {
        //현재 유저에 해당하는 CartList
        try {
            List<Cart> cartListByUserId = cartJPARepository.findByUserId(userDetails.getUser().getId());
            Order order = Order.builder()
                    .user(userDetails.getUser())
                    .build();
            orderJPARepository.save(order); //해당 order 영속화
            List<Item> itemList = new ArrayList<>();
            for (Cart cart : cartListByUserId) {
                Item a = Item.builder()
                        .price(cart.getPrice())
                        .quantity(cart.getQuantity())
                        .order(order)
                        .option(cart.getOption())
                        .build();
                itemJPARepository.save(a);
            }

            return itemList;
        }catch(RuntimeException e){
            throw new Exception400("server error");
        }
    }

    public List<Item> orderById(int id) {
        List<Item> itemList = itemJPARepository.findByOrderId(id);
        if(itemList.isEmpty()){
            throw new Exception400("존재하지 않는 주문ID 입니다");
        }
        return itemList;
    }
}
