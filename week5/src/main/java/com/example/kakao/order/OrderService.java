package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.cart.CartResponse;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    //결재하기(주문 인서트)
    @Transactional
    public OrderResponse.FindAllDTO save(User sessionUser) {
        // 사용자가 담은 장바구니가 없을때 예외처리
        int userId = sessionUser.getId();
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(userId); //Option, Product join fetch
        if(cartList.isEmpty()){
            throw new Exception400("담은 장바구니가 없습니다");
        }
        //1. 주문 생성하기
        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order);
        System.out.println("order 저장");

        //2. 장바구니를 Item에 저장하기
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = Item.builder().option(cart.getOption()).order(order)
                    .quantity(cart.getQuantity()).price(cart.getPrice()).build();
            itemJPARepository.save(item);
            itemList.add(item);
        }
        //3. 장바구니 초기화하기
        cartJPARepository.deleteByUserId(userId); //커스텀 쿼리 작성하여 필요없는 쿼리 줄임

        //4. DTO 응답
        return new OrderResponse.FindAllDTO(order, itemList);
    }

    //주문 결과 확인
    public OrderResponse.FindAllDTO findById(int id) {
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("존재하지않는 orderId 입니다")
        );
        List<Item> itemList = itemJPARepository.findByOrderId(id);
        return new OrderResponse.FindAllDTO(order, itemList);
    }
}

