package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user){

        List<Cart> carts = cartJPARepository.findAllWithOptionsUsingFetchJoinByUserId(user.getId());
        /**
         * 파라미터로 온 User는 id만 가지고 있는 객체! -> id를 가지고 있기 때문에 FK로서의 역할은 충분!
         * findAllWithOptionsUsingFetchJoinByUserId을 사용한 이유는 cart에 있는 option들까지 한번에 조회하려고!
         *
          */
        Order order = orderJPARepository.save(Order.createOrder(user));
        List<Item> items1 = itemJPARepository.saveAll(Item.createItems(carts, order));

        /**
         *  카트를 초기화해줘야한다!
         */
        cartJPARepository.deleteByUserId(user.getId());
        return new OrderResponse.FindByIdDTO(order,items1);

    }


    public OrderResponse.FindByIdDTO findById(int id){
        /**
         *  주문 아이디가 존재하는 아이디인지 확인해야한다.
         */
        Order order = orderJPARepository.findById(id).orElseThrow(() -> new Exception400("존재하지 않는 주문입니다."));
        List<Item> items = itemJPARepository.findItemsWithOptionUsingFetchJoinById(id);
        return new OrderResponse.FindByIdDTO(order, items);
    }


}
