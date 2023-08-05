package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJPARepository orderJPARepository;

    private final ItemJPARepository itemJPARepository;

    private final CartJPARepository cartJPARepository;

    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void save(List<OrderRequest.SaveDTO> requestDTOs, CustomUserDetails userDetails){

        // 해당 CartId의 cart가 동일한 cart인지, DB에 있는지 없으면 예외
        List<Cart> carts = requestDTOs.stream().distinct()
                .filter(dto -> cartJPARepository.findById(dto.getId()).isPresent())
                .map(dto -> cartJPARepository.findById(dto.getId()).get())
                .collect(Collectors.toList());

        if (carts.size() == 0){
            throw new Exception400("cart가 비어있습니다.");
        }

        // Option들
        List<Option> options = carts.stream().distinct()
                .map(cart -> optionJPARepository.findById(cart.getOption().getId()).get())
                .collect(Collectors.toList());


        // cart에 있는 값들을 Order에 저장하고, Item에 order 추가 cart를 삭제
        Order order = Order.builder()
                .user(userDetails.getUser())
                .build();

        Order orderSP = orderJPARepository.save(order);

        for (Option option : options) {
            Item item = Item.builder()
                    .order(orderSP)
                    .option(option)
                    .build();

            itemJPARepository.save(item);

        }

        cartJPARepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public OrderResponse.FindById findById(int id, UserDetails userDetails){
        // id 값으로 Order 찾아오기
        Optional<Order> order = orderJPARepository.findById(id);

        if (order.isEmpty()) {
            throw new Exception404("해당 Id 값의 주문이 존재하지 않습니다.");
        }

        Order orderSP = order.get();

        // 해당 order.id 값에 맞는 Item 값을 찾아오기
        List<Item> items = itemJPARepository.findByOrderIdJoinOrder(orderSP.getId());

        // Item.option 값으로 Product 값 가져오기
        List<Option> options = items.stream().map(item -> item.getOption()).collect(Collectors.toList());

        List<Product> products = options.stream().map(option -> option.getProduct()).collect(Collectors.toList());

        return new OrderResponse.FindById(orderSP, products, items);
    }
}
