package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.ProductService;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderRepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemRepository;
    @Transactional
    public OrderResponse.FindByIdDTO findById(int id) {

        // 주문에 해당하는 Item을 가져온다.
        List<Item> items = itemRepository.findByOrder(id);

        // items의 option에서 product를 추출한다
        return new OrderResponse.FindByIdDTO(id,items);

    }
    @Transactional
    public OrderResponse.SaveDTO save(User user) {
        // 새로운 주문 생성
        Order order = Order.builder().user(user).build();
        orderRepository.save(order);
        // user로부터 cartList를 가져온다
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        System.out.println("cartList = " + cartList);


        return null;
    }
}