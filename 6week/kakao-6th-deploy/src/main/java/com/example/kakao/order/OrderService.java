package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(
        readOnly = true
)
@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemJPARepository itemJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;



    @Transactional
    public OrderResponse.FindByIdDTO saveOrder(User user) {

        // 1. 해당 유저가 가지고 있는 카트 불러오기 (이대로 결재 진행할 예정)
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 2. 만약 존재하지 않는다면 throw 던져주기
        if (cartList.size() == 0) {
            throw new Exception404("장바구니에 아무 내역도 존재하지 않습니다");
        }

        // 3. 해당 유저의 order 객체 생성
        Order order = orderJPARepository.save(Order.builder().user(user).build());

        // 4. List<Cart> => List<Item>
        List<Item> itemList = cartList.stream()
                .map(cart -> Item.builder()
                        .option(cart.getOption())
                        .order(order)
                        .quantity(cart.getQuantity())
                        .price(cart.getOption().getPrice() * cart.getQuantity())
                        .build())
                .collect(Collectors.toList());

        // 5. itemList를 Item 테이블에 저장하기 (for 결재)
        this.itemJPARepository.saveAll(itemList);

        // 6. 카트에 있던 장바구니 목록 초기화 하기
        this.cartJPARepository.deleteByUserId(user.getId());

        // 7. DTO 만들어서 reponse
        OrderResponse.FindByIdDTO response = new OrderResponse.FindByIdDTO(order, itemList);

        return response;


    }

    public OrderResponse.FindByIdDTO findById(int id) {

        // 1. 해당 OrderId 주문 존재하는 지 확인
        Order order = orderJPARepository.findById(id).orElseThrow(() -> {
            throw new Exception404("해당 주문을 찾을 수 없습니다 : " + id);
        });
        
        // 2. OrderId로 itemList 가져오기
        List<Item> itemList = this.itemJPARepository.findAllByOrderId(id);

        // 3. DTO 만들어서 reponse
        return new OrderResponse.FindByIdDTO(order, itemList);
    }


}
