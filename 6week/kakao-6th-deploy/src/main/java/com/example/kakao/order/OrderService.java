package com.example.kakao.order;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public void saveOrder(User sessionUser){
        Order order = Order.builder().user(sessionUser).build();
        orderJPARepository.save(order); // 주문 기록을 저장합니다.
        List<Order> orderList = orderJPARepository.findOrdersByUser(sessionUser.getId());
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId());
        for(Cart cart : cartList){
            Item item = Item.builder().order(orderList.get(orderList.size()-1)).option(cart.getOption()).price(cart.getPrice()).quantity(cart.getQuantity()).build();
            itemJPARepository.save(item);
        }

        cartJPARepository.deleteByUserId(sessionUser.getId()); // 장바구니 지움.

        // 저장하고 장바구니 지워야 함.

        /*
        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
        for(Cart cart : cartList) {
            Order order = Order.builder().user(sessionUser).cart(cart).price(cart.getPrice()).build();
            orderJPARepository.save(order);
        }
        */

    }

    public OrderResponse.FindAllDTO findOrder(int id, int userPK){

        List<Order> orderList = orderJPARepository.findOrdersByUser(userPK);

        if(! (orderList.get(orderList.size()-1).getUser().getId() == userPK)){
            System.out.println("본인이 아닙니다.");
            throw new Exception400("본인이 주문한 내역이 아닙니다. 잘못된 접근입니다.");
        } else {
            System.out.println("시스템 알림: 본인이 맞습니다 본인인증이 되었습니다.");
        }




        // 없으면 예외처리
        if(!orderList.isEmpty()) {
            List<Item> itemList = itemJPARepository.findByOrder(orderList.get(orderList.size()-1).getId());
            System.out.println("시스템 알림: 주문이 존재합니다.\n");
            for(Item item : itemList){
                System.out.println("시스템 알림: 현재 itemList의 값: " + item.getOption().getOptionName());
            }
            return new OrderResponse.FindAllDTO(itemList, orderList.get(orderList.size()-1).getId());
           /* for(OrderResponse.FindAllDTO.OrderDTO.ItemDTO itemdto : itemDTO){
                System.out.println(itemdto.getOption().getOptionName() + "입니다.\n");
            } */
        } else {
            throw new Exception400("찾을 수 없습니다.");
        }
    }

    /*
    public OrderResponse.FindAllDTO findAll(List<Item> itemList){
        Optional<Order> order = orderJPARepository.findById(id);
        if(order.isPresent()){
            List<Item> item = itemJPARepository.findByOrder(o)
        }
    }
    */


}