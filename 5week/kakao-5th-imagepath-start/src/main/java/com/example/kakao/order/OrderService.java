package com.example.kakao.order;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.cart.CartRequest;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;

    private final ItemJPARepository itemJPARepository;

    public OrderResponse.saveDTO save(User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdJoinOptionJoinProduct(user.getId());

        //1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) throw new Exception400("주문할 상품이 없습니다. 장바구니에 상품을 먼저 담아주세요.");

        int userId = user.getId();
        Order order = Order.builder().user(user).build();

        List<Item> itemList = cartList.stream().map(c -> cartToItem(c, order)).collect(Collectors.toList());

        orderJPARepository.save(order);
        itemJPARepository.saveAll(itemList);
        cartJPARepository.deleteAllByUserId(userId);

        Map<Integer, List<Item>> groupedItemsMap = itemList.stream().collect(Collectors.groupingBy(i -> i.getOption().getProduct().getId()));
        List<OrderResponse.saveDTO.ProductDTO> productDTOs = itemsMapToProductDTOs(groupedItemsMap);
        OrderResponse.saveDTO result = new OrderResponse.saveDTO(order.getId(), productDTOs);
        return result;
    }

    private Item cartToItem(Cart cart, Order order) {
        return Item.builder()
                .option(cart.getOption())
                .order(order) //스트림 내부에서 외부 변수를 사용해도 괜찮을까?
                .quantity(cart.getQuantity())
                .price(cart.getPrice())
                .build();
    }

    private List<OrderResponse.saveDTO.ProductDTO> itemsMapToProductDTOs(Map<Integer, List<Item>> groupedItemsMap) {
        List<OrderResponse.saveDTO.ProductDTO> result = new ArrayList<>();
        Iterator<Integer> productIds = groupedItemsMap.keySet().iterator();
        while (productIds.hasNext()) {
            int productId = productIds.next();
            List<Item> groupedItems = groupedItemsMap.get(productId);

            result.add(new OrderResponse.saveDTO.ProductDTO(groupedItems.get(0).getOption().getProduct().getProductName(), groupedItems));
        }

        return result;
    }

    public OrderResponse.findByIdDTO findByOrderId(int id, User user) {
    return new OrderResponse.findByIdDTO();
    }
}
