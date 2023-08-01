package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.ProductResponse;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.FindByIdDTO save(User user){
        try {
            //주문 생성하기
            Order order = Order.builder()
                    .user(user)
                    .build();
            orderJPARepository.save(order);

            //장바구니 -> OrderItem으로 옮겨 저장하기
            List<Cart> cartList = cartJPARepository.findAllCartsByUserId(user.getId());
            Item item;
            List<Item> itemList = new ArrayList<>();
            for (Cart c : cartList) {
                item = Item.builder().order(order)
                        .option(c.getOption())
                        .quantity(c.getQuantity())
                        .price(c.getPrice())
                        .build();
                itemJPARepository.save(item);
                itemList.add(item);
            }
            //응답 DTO 생성
            OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
            return responseDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            throw new Exception500("unknown server error : service");
        }
    }

    public OrderResponse.FindByIdDTO findById(int id){
        try{
            Optional<Order> optional = orderJPARepository.findById(id);
            Order order = null;
            if(optional.isPresent()){ //주문이 존재할 경우
                order = optional.get();
                List<Item> itemList = itemJPARepository.findItemsByOrderId(id);
                OrderResponse.FindByIdDTO responseDTO = new OrderResponse.FindByIdDTO(order, itemList);
                return responseDTO;
            }
            else {
                throw new Exception404("해당 주문이 없습니다.");
            }
        }
        catch (Exception404 e){
            throw e;
        }
        catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
                throw new Exception500("unknown server error");
        }
    }

}
