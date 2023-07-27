package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartRepository;
    private final OptionJPARepository optionRepository;

    /* 장바구니 추가 Service Layer */
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        /* 1. 동일한 옵션이 들어올 경우의 예외처리 */
        // 1.1 requestDTOs를 순회하면서 optionId를 찾아 그 값을 optionIds 리스트에 저장
        List<Integer> optionIds = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .collect(Collectors.toList());
        // 1.2 optionIds 배열을 순회하며 중복되지 않는 원소 수 카운트
        long uniqueCount = optionIds.stream().distinct().count();
        // 1.3 하나라도 중복되는 값이 있다면 동일한 옵션이 들어온 경우이므로 400에러를 발생시킨다.
        if (uniqueCount != optionIds.size()) throw new Exception400("중복된 option 요청, 잘못된 요청입니다.");
        /* cartJPARepository.findByOptionAndUserId() 조회
         * -> 존재하면 장바구니에 수량을 추가하는 업데이트 수행[2]
         * -> 존재하지 않으면 유저의 장바구니에 담기[3] */
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            // optionId 받아오기
            int optionId = requestDTO.getOptionId();
            // userId, optionId으로 일치하는 장바구니 탐색
            Optional<Cart> optionalCart = cartRepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            /* 2. 일치하는 장바구니 존재, 장바구니에 수량을 추가하는 업데이트 수행 */
            if (optionalCart.isPresent()) {
                // 2.1 장바구니 가져오기, 이론상 isPresent()를 통해 null이 아님이 보장되므로 에러가 발생할 수가 없다.
                Cart cartPS = cartRepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).orElseThrow(
                        () -> new Exception500("Unknown Server Error")
                );
                // 2.2 option 가져오기, 별도의 쿼리 없이 장바구니에서 바로 가져올 수 있다.
                Option optionPS = cartPS.getOption();
                // quantity 계산
                int quantity = cartPS.getQuantity() + requestDTO.getQuantity();
                // price 계산
                int price = optionPS.getPrice() * quantity;
                // 2.3 cart 업데이트
                cartPS.update(quantity, price);
            }
            /* 3. 일치하는 장바구니 없음, 유저의 장바구니에 담기 */
            else {
                // quantity 받아오기
                int quantity = requestDTO.getQuantity();
                // 3.1 option가져오기, option이 존재하지 않을 경우 404 에러를 발생시킨다.
                Option optionPS = optionRepository.findById(optionId).orElseThrow(
                        () -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId)
                );
                // price 계산
                int price = optionPS.getPrice() * quantity;
                // 3.2 cart객체 build
                Cart cart = Cart.builder()
                        .user(sessionUser)
                        .option(optionPS)
                        .quantity(quantity)
                        .price(price)
                        .build();
                // 3.3 cart에 저장하기(장바구니 담기)
                cartRepository.save(cart);
            }
        }
    }

    /* 장바구니 조회 Service Layer */
    @Transactional
    public CartResponse.FindAllDTO findAll() {
        // cart 쿼리
        List<Cart> cartList = cartRepository.findAll();
        // Response DTO 반환
        return new CartResponse.FindAllDTO(cartList);
    }

    /* 장바구니 수정 Service Layer */
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        /* 장바구니 업데이트 예외처리 */
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (requestDTOs == null || requestDTOs.isEmpty())
            throw new Exception400("장바구니가 비어 있습니다, 잘못된 요청입니다.");
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<Integer> requestCartIds = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toList());
        long uniqueCount = requestCartIds.stream().distinct().count();
        if (uniqueCount != requestCartIds.size())
            throw new Exception400("중복된 cart에 대한 update 요청, 잘못된 요청입니다.");
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId()).orElseThrow(
                () -> new Exception400("장바구니가 비어 있습니다, 잘못된 요청입니다")
        );
        Set<Integer> userCartIds = cartList.stream()
                .map(Cart::getId)
                .collect(Collectors.toSet());
        for (CartRequest.UpdateDTO dto : requestDTOs)
            if (!userCartIds.contains(dto.getCartId()))
                throw new Exception400("유저 장바구니에 없는 cartId 요청입니다, 잘못된 요청입니다.");
        /* 장바구니 최종 업데이트 */
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(),
                            cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        // Response DTO 반환
        return new CartResponse.UpdateDTO(cartList);
    }
}
