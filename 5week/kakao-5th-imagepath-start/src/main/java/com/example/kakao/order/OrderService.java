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
        List<OrderResponse.saveDTO.ProductDTO> productDTOs = itemsMapToSaveProductDTOs(groupedItemsMap);
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

    private List<OrderResponse.saveDTO.ProductDTO> itemsMapToSaveProductDTOs(Map<Integer, List<Item>> groupedItemsMap) {
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
        Optional<Order> orderOP = orderJPARepository.findById(id);
        //1. 존재하지 않는 주문번호를 조회할 경우
        Order order;
        if (orderOP.isEmpty()) throw new Exception404("존재하지 않는 주문번호입니다. :" + id);
        order = orderOP.get();

        //2. 다른 사용자의 주문번호를 조회할 경우
        if (order.getUser().getId() != user.getId()) throw new Exception400("접근 권한이 없습니다.");

        List<Item> itemList = itemJPARepository.findAllByOrderIdJoinOptionJoinProduct(id);

        Map<Integer, List<Item>> groupedItemsMap = itemList.stream().collect(Collectors.groupingBy(i -> i.getOption().getProduct().getId()));
        List<OrderResponse.findByIdDTO.ProductDTO> productDTOs = itemsMapToFindByIdProductDTOs(groupedItemsMap);
        OrderResponse.findByIdDTO result = new OrderResponse.findByIdDTO(order.getId(), productDTOs);

        return result;
    }

    private List<OrderResponse.findByIdDTO.ProductDTO> itemsMapToFindByIdProductDTOs(Map<Integer, List<Item>> groupedItemsMap) {
        List<OrderResponse.findByIdDTO.ProductDTO> result = new ArrayList<>();
        Iterator<Integer> productIds = groupedItemsMap.keySet().iterator();
        while (productIds.hasNext()) {
            int productId = productIds.next();
            List<Item> groupedItems = groupedItemsMap.get(productId);

            result.add(new OrderResponse.findByIdDTO.ProductDTO(groupedItems.get(0).getOption().getProduct().getProductName(), groupedItems));
        }

        return result;
    }
}
