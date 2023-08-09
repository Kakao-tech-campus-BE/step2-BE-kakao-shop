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

    public OrderResponse.FindByIdDTO findById(int orderId, User user) {
        // 1. 주문 존재 확인
        Order order = getValidOrderFindById(orderId);

        // 2. 유효한 사용자가 접근했는지 확인
        validOrderAccess(order.getUser().getId(), user);

        // 3. 주문 상품 가져오기
        List<Item> itemList = getValidItemFindByOrderId(orderId);

        // 4. 주문 결과 반환
        return new OrderResponse.FindByIdDTO(order, itemList);
    }

    // --------- private ---------

    /**
     * 주문을 저장한다. 데이터베이스 문제가 있을 경우 OrderSaveException을 발생시킨다.
     */
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

    /**
     * 주문 상품을 저장한다. 데이터베이스 문제가 있을 경우 ItemSaveException을 발생시킨다.
     */
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

    /**
     * 입력한 장바구니 데이터를 제거한다.
     * 데이터베이스 문제가 있을 경우 CartDeleteException을 발생시킨다.
     */
    private void clearCartList(List<Cart> cartList) {
        try{
            // 삭제할 엔티티들을 1번의 쿼리로 해결해준다.
            cartJPARepository.deleteAllIn(cartList.stream().map(Cart::getId).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new CartException.CartDeleteException(e.getMessage());
        }
    }

    /**
     * 주문의 작성자 본인 혹은 관리자가 아니라면 ForbiddenOrderAccess 예외를 발생시킨다.
     */
    private void validOrderAccess(int orderUserId, User user) {
        if(orderUserId != user.getId() && !user.getRoles().contains("ROLE_ADMIN")) throw new OrderException.ForbiddenOrderAccess();
    }

    /**
     * 회원ID로 장바구니를 조회한다. 이때, 장바구니가 비어있다면 CartNotFoundException 예외를 발생시킨다.
     */
    private List<Cart> getValidCartFindAllByUserId(int userId) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdFetchJoin(userId);
        if(cartList.isEmpty()) throw new CartException.CartNotFoundException();
        return cartList;
    }

    /**
     * 주문ID로 주문 상품목록을 조회한다. 이때, 주문 상품목록이 비어있다면 ItemNotFoundException 예외를 발생시킨다.
     */
    private List<Item> getValidItemFindByOrderId(int orderId) {
        List<Item> itemList = itemJPARepository.findByOrderId(orderId);
        if(itemList.isEmpty()) throw new ItemException.ItemNotFoundException();
        return itemList;
    }

    /**
     * 주문ID로 주문을 조회한다. 이때, 존재하지 않는 주문이라면 OrderNotFoundException 예외를 발생시킨다.
     */
    private Order getValidOrderFindById(int orderId) {
        return orderJPARepository.findById(orderId).orElseThrow(
                () -> new OrderException.OrderNotFoundException(orderId));
    }
}
