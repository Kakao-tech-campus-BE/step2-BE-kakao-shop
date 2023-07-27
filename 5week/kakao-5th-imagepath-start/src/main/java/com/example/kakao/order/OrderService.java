package com.example.kakao.order;

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

    private final CartJPARepository cartJPARepository;
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;

    //특히나 결제는 트랜잭션 관리를 잘 해야한다.
    @Transactional
    public OrderResponse.FindAllDTO orderAll(User user){
        List<Cart> cartList =  cartJPARepository.findAllByUserId(user.getId());
        if (cartList.isEmpty()){
            throw new Exception404("장바구니가 비어있습니다.");
        }

        Order newOrder = Order.builder()
                .user(user).build();
        orderJPARepository.save(newOrder);

        int totalPrice = 0;

        List<Item> items = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(newOrder)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            items.add(item);
            totalPrice += cart.getPrice();
        }
        // 쿼리 단 하나
        itemJPARepository.saveAll(items);
        /*유저 장바구니 전부 삭제
        강사님이 제공해주신 메서드이지만 이런 경우에는 id 마다 따로 삭제를 진행해주어야 해서 deleteAll을 사용함
        cartJPARepository.deleteByUserId(user.getId());*/

        // 쿼리 단 하나
        cartJPARepository.deleteAll(cartList);
        System.out.println(totalPrice);
        return new OrderResponse.FindAllDTO(newOrder.getId(), cartList, totalPrice);
    }

    public OrderResponse.FindByIdDTO findById (int orderId, User user) {
        // 권한 체크
        boolean hasAccess = orderJPARepository.existsByIdAndUserId(orderId, user.getId());
        if (!hasAccess) {
            throw new Exception401("해당 주문이 유효하지 않거나 접근권한이 없습니다.");
        }

        // 주문 내 상품 목록 가져오기
        List<Item> itemList = itemJPARepository.findWithOptionAndProductByOrderId(orderId);

        // 총 주문 금액 계산
        // Order을 만들 당시에 TotalPrice를 저장하는 변수를 만드는것이 좋을것 같다.
        int totalPrice = itemList.stream()
                .mapToInt(item -> item.getPrice())
                .sum();

        // 응답 DTO 생성
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(orderId, itemList, totalPrice);

        return responseDTO;
    }


}
