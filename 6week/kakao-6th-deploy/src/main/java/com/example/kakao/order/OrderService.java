package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
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
import java.util.Optional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

	private final CartJPARepository cartJPARepository;
	private final OrderJPARepository orderJPARepository;
	private final ItemJPARepository itemJPARepository;
	
    @Transactional
    public OrderResponse.SaveDTO saveOrder(User sessionUser) {
    	// 1. 장바구니 리스트 가져오기
    	List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
    		    
    	if (cartList.isEmpty()) {
    		throw new Exception400("장바구니가 비어 있습니다.");
    	}
    		    
    	// 2. 결제하기 -> 생략
    		    
    	// 3. 주문정보 저장
    	Order order = orderJPARepository.save(Order.builder().user(sessionUser).build());
    	for (Cart cart : cartList) {
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            itemJPARepository.save(item);
        }
    	
    	// 4. 장바구니 비우기
    	cartJPARepository.deleteByUserId(sessionUser.getId());

    	// 5. 응답 반환
    	OrderResponse.SaveDTO response = new OrderResponse.SaveDTO(cartList);
    	response.setId(order.getId());

    	return response;
    }
    
    @Transactional
    public OrderResponse.findOrderDTO getOrderById(User sessionUser, int id) {
    	// 1. User가 가진 Order 가져오기
        List<Order> orders = orderJPARepository.findAllByUser(sessionUser);

        // 2. 만약 요청한 주소에 대한 접근 권한이 없을 경우 & 유저가 주문내역이 비어있을 경우 404 에러
        // 접근 권한이 없더라도 보안상 403보다는 404를 때리는 것이 좋겠다고 생각했습니다. 
        if (orders.isEmpty()) {
            throw new Exception404("주문이 존재하지 않습니다 :" + id);
        }
        
        Optional<Order> order = orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        
        if (!order.isPresent()) {
        	throw new Exception404("주문을 확인할 수 없습니다 :" + id);
        }

        // 3. 요청한 주소에 대한 접근 권한이 있을 경우 Order id를 이용해 item 조회
        List<Item> items = itemJPARepository.findAllByOrderId(id);
        
        // 4. 없는 요청을 했을 경우 404
        if (items.isEmpty()) {
        	throw new Exception404("주문 내역이 존재하지 않습니다 :" + id);
        }

        // 5. 응답 반환
        OrderResponse.findOrderDTO response = new OrderResponse.findOrderDTO(items);
        response.setId(id);
        return response;
    }
    
}