package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;


    @Transactional
    public OrderResponse.FindByIdDTO save(User user){
        //save로직 발동 시 유저에 해당하는 카트데이터를 갖고옴. request로 cart의 데이터를 받아올수도 있지만 그 때 데이터를 검사하려면 cart, option, product 3개의 테이블과
        //일치하는지 확인해야 하기 때문에 검증된 데이터인 cartTable에서 cart정보를 호출하여 주문한다.
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());

        //먼저 유저가 아무것도 장바구니에 넣지 않았다면 비어있을 가능성이 있어보인다. 그렇다면 cartList는 비어있는 리스트이기 때문에 예외처리를 해줘야 한다.
        if (cartList.isEmpty()){
            throw new Exception403("장바구니에 상품을 추가해주십시오");
        }

        List<Item> itemList = new ArrayList<>();
        //주문 객체를 생성
        Order order = Order.builder().user(user).build();
        //주문에 담길 아이템 객체들을 전달받은 cartList에서 추출하여 대입한다.-->itemList를 생성하는 과정
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
        //저장이 순조롭게 완료되었다면 cart의 내역을 삭제해줘야 한다. cartRepository에 만들어놨던 deleteByUserId를 통해 한번에 삭제해준다
        //이렇게 한번에 delete하는 쿼리는 벌크연산을 진행하는데 벌크연산을 위해서는 @Modifying을 repository에 붙여준다.
        cartJPARepository.deleteByUserId(user.getId());
        //responseDTO로는 최종 주문 결과 표시를 위해 findByIdDTO를 통해 product, item이 모두 담기게 반환
        //최종주문결과를 위해 가장 최근에 저장된 주문내역을 호출해야할 것 같다..-->아래의 쿼리를 발생시킴
        //가장 최근의 주문을 불러오고 싶은데 limit 사용이 안되기 때문에 page객체를 통해 불러왔다.
        Pageable limitOne = PageRequest.of(0, 1);
        List<Item> lastOrderItem = itemJPARepository.findByUserIdOrderByOrderIdLimitN(user.getId(), limitOne);
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(lastOrderItem);
        return responseDTO;
    }

    @Transactional
    public OrderResponse.FindByIdDTO findById(User user, int id){
        //주문내역을 id로 검색했을 때 없는 주문번호라면 404헌납. 또한 유저는 자기자신의 주문내역만 접근할 수 있게한다.
        //훔쳐보기 금지
        Optional<Order> orderOP = orderJPARepository.findByUserIdAndOrderId(user.getId(), id);
        if (orderOP.isEmpty()){
            throw new Exception404("유저의 주문내역이 존재하지 않습니다.");
        }

        //주문내역이 존재한다면 해당 id로 item을 찾아 넘긴다.
        //이때 위에서 한번 주문을 찾았는데 그렇다면 굳이 한번 더 join할 필요가 없을수도 있겠다.
        List<Item> items = itemJPARepository.findByOrderId(id);
        OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(items);
        return responseDTO;
    }
}