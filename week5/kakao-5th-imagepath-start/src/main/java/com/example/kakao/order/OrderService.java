package com.example.kakao.order;

import com.example.kakao._core.errors.exception.cart.CartException;
import com.example.kakao._core.errors.exception.order.Item.ItemException;
import com.example.kakao._core.errors.exception.order.OrderException;
import com.example.kakao.cart.Cart;
import com.example.kakao.cart.CartJPARepository;
import com.example.kakao.order.item.Item;
import com.example.kakao.order.item.ItemJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderJPARepository orderJPARepository;
    private final ItemJPARepository itemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.CreateDTO create(User user) {

        // 1. 주문 전 장바구니 확인
        List<Cart> cartList = getValidCartFindAllByUserId(user.getId());

        // 2. 주문 생성
        Order order = saveOrder(user);

        // 3. 주문제품 생성
        List<Item> itemList = saveItemList(cartList, order);

        // 4. 장바구니 비우기
        clearCartList(cartList);

        return new OrderResponse.CreateDTO(order, itemList);
    }

    public OrderResponse.FindByIdDTO findById(int orderId, int userId) {
        // 1. 주문 존재 확인
        Order order = getValidOrderFindById(orderId);

        // 2. 유효한 사용자가 접근했는지 확인
        validOrderAccess(order.getUser().getId(), userId);

        // 3. 주문 상품 가져오기
        List<Item> itemList = getValidItemFindByOrderId(orderId);

        // 4. 주문 결과 반환
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    // --------- private ---------

    private Order saveOrder(User user) {
        Order order = Order.builder()
                .user(user)
                .build();
        try {
            orderJPARepository.save(order);
        } catch (Exception e) {
            throw new OrderException.OrderSaveException(e.getMessage());
        }
        return order;
    }

    private List<Item> saveItemList(List<Cart> cartList, Order order) {
        List<Item> itemList = cartList.stream()
                .map( c -> Item.builder()
                        .order(order)
                        .option(c.getOption())
                        .quantity(c.getQuantity())
                        .price(c.getPrice())
                        .build())
                .collect(Collectors.toList());

        try{
            itemJPARepository.saveAll(itemList);
        }catch(Exception e) {
            throw new ItemException.ItemSaveException(e.getMessage());
        }
        return itemList;
    }

    private void clearCartList(List<Cart> cartList) {
        try{
            // 삭제할 엔티티들을 1번의 쿼리로 해결해준다.
            cartJPARepository.deleteAllInBatch(cartList);
        } catch (Exception e) {
            throw new CartException.CartDeleteException(e.getMessage());
        }
    }

    private void validOrderAccess(int orderUserId, int userId) {
        if(orderUserId != userId) throw new OrderException.ForbiddenOrderAccess();
    }

    private List<Cart> getValidCartFindAllByUserId(int userId) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdFetchJoin(userId);
        if(cartList.isEmpty()) throw new CartException.CartNotFoundException();
        return cartList;
    }

    private List<Item> getValidItemFindByOrderId(int orderId) {
        List<Item> itemList = itemJPARepository.findByOrderId(orderId);
        if(itemList.isEmpty()) throw new ItemException.ItemNotFoundException();
        return itemList;
    }

    private Order getValidOrderFindById(int orderId) {
        return orderJPARepository.findById(orderId).orElseThrow(
                () -> new OrderException.OrderNotFoundException(orderId));
    }
}
