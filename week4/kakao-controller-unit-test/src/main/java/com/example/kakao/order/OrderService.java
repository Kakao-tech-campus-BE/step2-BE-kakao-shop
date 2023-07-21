package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductResponse;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;

    private final ItemJPARepository itemJPARepository;

    private final UserJPARepository userJPARepository;

    public OrderResponse.FindByIdDTO save(OrderResponse.FindByIdDTO requestDTO){
        OrderResponse.FindByIdDTO responseDTO = requestDTO;
        Optional<User> user = userJPARepository.findById(1);
        try {
            //server에러인 것은 좋지 않지만 유저 입력도 없었는데 서비스단에서 오류가 발생했다는 것은 엄밀한 실수이다.
            //save로직 발생 가정
            Order order = new Order(requestDTO.getId(),user.get());
            orderJPARepository.save(order);
            return responseDTO;
        } catch (Exception e) {
            throw new Exception500("서버에서 오류가 발생했습니다.");
        }
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
