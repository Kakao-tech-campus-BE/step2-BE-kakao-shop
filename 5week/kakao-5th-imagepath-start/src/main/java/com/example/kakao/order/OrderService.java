package com.example.kakao.order;

import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    public OrderResponse.saveDTO save(User sessionUser) {
        List<Cart> cartList = cartJPARepository.mFindAllByUserId(sessionUser.getId());
        Order order = orderSave(sessionUser);
        List<Item> itemList = cartToItem(cartList,order);
        itemSave(itemList);

        cartJPARepository.deleteByUserId(sessionUser.getId());
        return OrderResponse.saveDTO.builder()
                .id(order.getId())
                .itemList(itemList)
                .build();
    }


    private Order orderSave (User sessionUser){
            Order order = Order.builder()
                    .user(sessionUser)
                    .build();
            orderJPARepository.save(order);
            return order;
        }

        private List<Item> cartToItem (List<Cart> cartList, Order order){
            return cartList.stream().map(
                    x -> Item.builder()
                            .price(x.getPrice())
                            .quantity(x.getQuantity())
                            .order(order)
                            .option(x.getOption())
                            .build()
            ).collect(Collectors.toList());
        }

        private void itemSave(List<Item> itemList) {
            for(Item item : itemList)
                itemJPARepository.save(item);
        }

        private List<OrderResponse.ItemDTO> itemToItemDTO(List<Item> itemList) {
            return itemList.stream()
                    .map(item -> OrderResponse.ItemDTO.builder()
                            .item(item)
                            .build())
                    .collect(Collectors.toList());
        }


        public OrderResponse.saveDTO findAll (int id){
            List<Item> itemList = itemJPARepository.findByOrderId(id);
            boolean containsNull = itemList.stream().anyMatch(Objects::isNull);
            if(containsNull) {
                throw new IllegalArgumentException("item 리스트에 null이 들어갔거나 orderId에 해당하는 item을 찾을 수 없습니다");
            }
            return OrderResponse.saveDTO.builder()
                    .id(id)
                    .itemList(itemList)
                    .build();
        }
    }
