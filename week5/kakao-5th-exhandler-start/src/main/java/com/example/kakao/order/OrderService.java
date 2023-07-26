package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.FindByIdDTO save(User user){
        //save로직 발동 시 유저에 해당하는 카트데이터를 갖고옴.
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        List<Item> itemList = new ArrayList<>();
        //주문 객체를 생성
        Order order = Order.builder().user(user).build();
        //주문에 담길 아이템 객체들을 전달받은 cartList에서 추출하여 대입한다.
        for (Cart cart : cartList) {
             Item item= Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
             itemList.add(item);
        }
        //item객체 생성완료 후, 각각의 테이블에 데이터를 저장하는 과정
        orderJPARepository.save(order);
        //item은 같은 주문id에 여러개가 들어갈 수 있기 때문에 saveAll
        itemJPARepository.saveAll(itemList);
        //저장이 순조롭게 완료되었다면 cart의 내역을 삭제해줘야 한다. deleteAll을 통해 한번에 삭제해주자..
        //근데 deleteAll의 장점이 있나?
        cartJPARepository.deleteAll(cartList);
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
        //responseDTO로는 최종 주문 결과 표시를 위해 findByIdDTO를 통해 product, item이 모두 담기게 반환
        return responseDTO;
    }

    public OrderResponse.FindByIdDTO findById(int id){
        //주문내역을 id로 검색했을 때 없는 주문번호라면 404헌납
        Order order = orderJPARepository.findById(id).orElseThrow(
                ()-> new Exception404("주문내역이 존재하지 않습니다."));
        List<Item> items = itemJPARepository.findByOrderId(id);
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, items);
        return responseDTO;
    }
}