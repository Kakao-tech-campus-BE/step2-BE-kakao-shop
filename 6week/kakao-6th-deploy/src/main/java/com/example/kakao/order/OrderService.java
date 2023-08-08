package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception401;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    //save되었을 때 장바구니를 비워야한다!!
    @Transactional
    public OrderResponse.SaveOrderDTO saveOrder(User sessionUser) {
        //카트 목록 데이터 가져옴
        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
        //카트가 비어있을때
        if(cartList.isEmpty()){
            throw new Exception404("장바구니에 아무 내역도 존재하지 않습니다");
        }

        //user 정보를 담은 order 객체 하나 만들고 저장
        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order);

        List<Item> itemList = new ArrayList<>();

        //Cart에 있는 물품 Item에 저장하기
        for (Cart cart : cartList) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            itemList.add(item);
        }

        itemJPARepository.saveAll(itemList);
        //장바구니 비우기
        cartJPARepository.deleteByUserId(sessionUser.getId());
        return new OrderResponse.SaveOrderDTO(order, itemList);
    }


    public OrderResponse.findByIdDTO findById(int id, User sessionUser){

        Order order = orderJPARepository.findOrderById(id).orElseThrow(
                ()-> new Exception400("해당 주문을 찾을 수 없습니다 : "+ id)
        );

        if(order.getUser().getId() != sessionUser.getId()){
            throw new Exception401("인증이 안되어있습니다.");
        }

        List<Item> itemList = itemJPARepository.mFindItemIdByJoin(order.getId());

        return new OrderResponse.findByIdDTO(order, itemList);
    }
}