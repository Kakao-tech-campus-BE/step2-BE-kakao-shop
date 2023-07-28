package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        Map<Integer, Integer> optionIdToQuantityMap = consolidateQuantities(requestDTOs);

        for (Integer optionId : optionIdToQuantityMap.keySet()) {
            Option option = findOption(optionId);
            int quantity = optionIdToQuantityMap.get(optionId);
            int price = option.getPrice() * quantity;

            Cart cart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).orElse(null);
            if (cart != null) {
                cartJPARepository.updateQuantityAndPrice(cart.getId(), cart.getQuantity()+quantity, cart.getPrice()+price);
            } else {
                Cart newCart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(price).build();
                cartJPARepository.save(newCart);
            }
        }
    }

    private Map<Integer, Integer> consolidateQuantities(List<CartRequest.SaveDTO> requestDTOs) {
        Map<Integer, Integer> optionIdToQuantityMap = new HashMap<>();
        for (CartRequest.SaveDTO requestDTO: requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            optionIdToQuantityMap.put(optionId, optionIdToQuantityMap.getOrDefault(optionId, 0) + quantity);
        }
        return optionIdToQuantityMap;
    }

    private Option findOption(Integer optionId) {
        return optionJPARepository.findById(optionId)
                .orElseThrow(() -> new Exception404("Could not find that option: " + optionId));
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 사용자의 장바구니가 비어있으면 예외를 발생시킵니다.
        if(cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어 있습니다.");
        }

        // 2. requestDTOs에 동일한 장바구니 ID가 두 번 입력되면 예외를 처리합니다. 예를 들어, cartId:1, cartId:1과 같은 경우입니다.
        Set<Integer> cartIdSet = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toSet());
        if(cartIdSet.size() != requestDTOs.size()) {
            throw new Exception400("동일한 장바구니 ID가 중복 되었습니다.");
        }
        // 3. 사용자의 장바구니에 없는 cartId가 입력되면 예외를 처리합니다.
        Set<Integer> userCartIdSet = cartList.stream()
                .map(Cart::getId)
                .collect(Collectors.toSet());
        if(!userCartIdSet.containsAll(cartIdSet)) {
            throw new Exception400("장바구니에 없는 ID가 있습니다.");
        }

        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO: requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    }

}
