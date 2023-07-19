package com.example.kakao.order;
import com.example.kakao.cart.Cart;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.user.UserJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final UserJPARepository userJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    public OrderService(UserJPARepository userJPARepository, OrderJPARepository orderJPARepository, CartJPARepository cartJPARepository, ItemJPARepository itemJPARepository) {
        this.userJPARepository = userJPARepository;
        this.orderJPARepository = orderJPARepository;
        this.cartJPARepository = cartJPARepository;
        this.itemJPARepository = itemJPARepository;
    }

    public OrderResponse.FindByIdDTO save(int userId) {
        User user = userJPARepository.findById(userId).orElseThrow(() -> new Exception400("해당 유저를 찾을 수 없습니다."));
        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);
        List<Cart> carts = cartJPARepository.mFindAllByUserId(userId); // 카트를 가져올 때 옵션을 가져오는 것이 성능이 좋음, todo 지금은 유저/상품/옵션을 페치조인해오는 쿼리다. 필요없는건 빼도 될듯
        List<Item> items = new ArrayList<>();
        for(Cart cart:carts){
            Item item = Item.builder().option(cart.getOption()).quantity(cart.getQuantity()).order(order).price(cart.getPrice()).build();
            items.add(item);
        }
        itemJPARepository.saveAll(items);
        return new OrderResponse.FindByIdDTO(order,items);
    }

    public OrderResponse.FindByIdDTO findById(int orderId, int userId) {
        List<Item> items =itemJPARepository.mFindAllByOrderId(orderId);
        if (items.isEmpty()){
            return null; //빈 값 반환, todo 반환을 뭘로 할지 생각해보기
        }
        if (items.get(0).getOrder().getId()!=userId){
            throw new Exception400("요청한 유저의 주문이 아닙니다.");
        }
        return new OrderResponse.FindByIdDTO(items.get(0).getOrder(),items);

    }
}
