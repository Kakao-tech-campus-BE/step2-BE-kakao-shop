package com.example.kakao.cart;

import com.example.kakao._core.errors.GlobalExceptionHandler;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Transactional
    public void create(List<CartRequest.SaveDTO> requestDTOs, CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        List<Integer> requestOptionIds = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .collect(Collectors.toList());

        List<Option> optionList = optionJPARepository.findByIdIn(requestOptionIds);

        // 옵션 존재 여부 확인
        if(optionList.size() != requestOptionIds.size()) {
            List<Integer> notExistOptionIds = findNotExistIds(requestOptionIds, optionList.stream().map(Option::getId).collect(Collectors.toList()));
            throw new Exception404("상품이 존재하지 않습니다.:" + notExistOptionIds);
        }

        // 장바구니에 해당 옵션 데이터가 있는지 확인하기 위한 작업
        List<Cart> cartList = findByUserIdAndOptionIdIn(requestOptionIds, user.getId());

        // 조회하기 쉽게 optionID로 그룹화 진행
        Map<Integer, Option> optionGroupByOptionId = new HashMap<>();
        Map<Integer, Cart> cartGroupByOptionId = new HashMap<>();
        for(Cart cart: cartList) {
            cartGroupByOptionId.put(cart.getOption().getId(), cart);
        }
        for(Option option: optionList) {
            optionGroupByOptionId.put(option.getId(), option);
        }

        // 카트에 이미 있는 옵션의 경우 업데이트, 없다면 추가
        for(CartRequest.SaveDTO dto : requestDTOs) {
            if(cartGroupByOptionId.containsKey(dto.getOptionId())) {
                int newQuantity = cartGroupByOptionId.get(dto.getOptionId()).getQuantity() + dto.getQuantity();
                int newPrice = cartGroupByOptionId.get(dto.getOptionId()).getOption().getPrice() * newQuantity;
                cartGroupByOptionId.get(dto.getOptionId()).update(newQuantity, newPrice);
            } else {
                cartList.add(Cart.builder()
                        .option(optionGroupByOptionId.get(dto.getOptionId()))
                        .user(user)
                        .quantity(dto.getQuantity())
                        .price(dto.getQuantity() * optionGroupByOptionId.get(dto.getOptionId()).getPrice())
                        .build()
                );
            }
        }

        try {
            cartJPARepository.saveAll(cartList);
        } catch(Exception e) {
            throw new Exception500("장바구니 저장 중 오류가 발생했습니다." + e.getMessage());
        }
    }

    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs) {
        List<Integer> requestCartIds = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toList());

        List<Cart> cartList = cartJPARepository.findByIdIn(requestCartIds);

        if(requestCartIds.size() != cartList.size()) {
            List<Integer> notExistOptionIds = findNotExistIds(requestCartIds, cartList.stream().map(Cart::getId).collect(Collectors.toList()));
            throw new Exception404("주문이 존재하지 않습니다.:" + notExistOptionIds);
        }

        Map<Integer, Cart> cartGroupByCartId = new HashMap<>();
        for(Cart cart: cartList) {
            cartGroupByCartId.put(cart.getId(), cart);
        }

        for(CartRequest.UpdateDTO dto : requestDTOs) {
            if(cartGroupByCartId.containsKey(dto.getCartId())) {
                int newQuantity = dto.getQuantity();
                int newPrice = cartGroupByCartId.get(dto.getCartId()).getOption().getPrice() * newQuantity;
                cartGroupByCartId.get(dto.getCartId()).update(newQuantity, newPrice);
            }
        }

        try {
            cartJPARepository.saveAll(cartList);
        }catch(Exception e) {
            throw new Exception500("장바구니 수정 중 오류가 발생했습니다." + e.getMessage());
        }
        return new CartResponse.UpdateDTO(cartList);
    }

    public CartResponse.FindAllDTO getCartLists(CustomUserDetails userDetails) {
        List<Cart> cartList = findByUserId(userDetails.getUser().getId());

        return new CartResponse.FindAllDTO(cartList);
    }

    private List<Cart> findByUserId(int userId) {
         return cartJPARepository.findByUserId(userId);
    }

    private List<Cart> findByUserIdAndOptionIdIn(List<Integer> optionIds, int userId) {
        return cartJPARepository.findByUserIdAndOptionIdIn(optionIds, userId);
    }

    private List<Integer> findNotExistIds(List<Integer> requestIds, List<Integer> databaseIds) {
        Set<Integer> setRequestIds = new HashSet<>(requestIds);

        Set<Integer> setDatabaseIds = new HashSet<>(databaseIds);

        setRequestIds.removeAll(setDatabaseIds);
        return new ArrayList<>(setRequestIds);
    }
}
