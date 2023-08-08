package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;



    //제공해주신 코드를 베이스로 짠 코드
    @Transactional
    public void addCartListLecture(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        // 1. 중복 검사
        validCartIdDuplicated(requestDTOs, CartRequest.SaveDTO::getOptionId);

        // 2 & 3. 장바구니 항목 업데이트 또는 추가
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            addOrUpdateCartItem(requestDTO, sessionUser);
        }
    }



    //장바구니 전체를 가져오는 방식 (해시로 순회)
    //쿼리가 여러번 날아간다. 하지만 장바구니 수량 수정이 잦은 사용자에게는 Hash 특성으로 더 효율적일듯하다
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        //1. 전체를 가져왔다.
        List<Cart> userCarts =cartJPARepository.findAllByUserId(sessionUser.getId());

        // 2. 전체 조회 결과를 Map 형태로 변환
        Map<Integer, Cart> cartMap = userCarts.stream()
                .collect(Collectors.toMap(cart -> cart.getOption().getId(), cart -> cart));

        // 3. requestDTOs 순회 및 장바구니 처리
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Option option = validAndGetOption(requestDTO.getOptionId());
            Cart existingCart = cartMap.get(requestDTO.getOptionId());
            if (existingCart != null) {
                addCartQuantity(existingCart, requestDTO.getQuantity(), option.getPrice());
                continue;
            }
            addNewCartItem(sessionUser, option, requestDTO.getQuantity());
            }
        }

    // 업데이트 하는 경우
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {

        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        validateCartConditions(cartList,requestDTOs);
        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.

        // Map 구조를 사용하여 더 효율적으로 처리.
        Map<Integer, CartRequest.UpdateDTO> updateDTOMap = requestDTOs.stream()
                .collect(Collectors.toMap(CartRequest.UpdateDTO::getCartId, Function.identity()));

        processCartUpdates(cartList, updateDTOMap);

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹


    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }
    private void addCartQuantity(Cart existingCart, int quantity, int pricePerItem) {
        existingCart.update(existingCart.getQuantity() + quantity, pricePerItem * (existingCart.getQuantity()+quantity));
    }

    // 업데이트 하는 경우
    private void updateCartQuantity(Cart existingCart, int quantity, int pricePerItem) {
        existingCart.update(quantity, pricePerItem * quantity);
    }

    // 추가하는 경우
    private void addNewCartItem(User user, Option option, int quantity) {
        int price = option.getPrice() * quantity;
        Cart cart = Cart.builder()
                .user(user)
                .option(option)
                .quantity(quantity)
                .price(price)
                .build();

        cartJPARepository.save(cart);
    }
    private void addOrUpdateCartItem(CartRequest.SaveDTO requestDTO, User sessionUser) {
        Option option = validAndGetOption(requestDTO.getOptionId());

        Optional<Cart> existingCartOpt = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());

        if (existingCartOpt.isPresent()) {
            addCartQuantity(existingCartOpt.get(), requestDTO.getQuantity(), option.getPrice());
            return;
        }
        addNewCartItem(sessionUser, option, requestDTO.getQuantity());
    }

    //메서드 분리
    //고차 함수로 DTO내에 중복 id 를 체크하였다.
    private <T> void validCartIdDuplicated(List<T> dtos, Function<T, Integer> idExtractor) {
        Set<Integer> uniqueIds = dtos.stream()
                .map(idExtractor)
                .collect(Collectors.toSet());
        if (uniqueIds.size() != dtos.size()) {
            throw new IllegalArgumentException("중복된 id가 존재합니다.");
        }
    }

    private Option validAndGetOption(int optionId) {
        return optionJPARepository.findById(optionId)
                .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
    }

    private void validateCartConditions(List<Cart> cartList, List<CartRequest.UpdateDTO> requestDTOs) {
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다!");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        validCartIdDuplicated(requestDTOs, CartRequest.UpdateDTO::getCartId);
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        Set<Integer> cartIds = cartList
                .stream()
                .map(Cart::getId)
                .collect(Collectors.toSet());

        requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .filter(requestCartId -> !cartIds.contains(requestCartId))
                .findFirst()
                .ifPresent(id -> {
                    throw new Exception404("유저의 장바구니에 없는 cartId가 있습니다: " + id);
                });
    }

    private void processCartUpdates(List<Cart> cartList, Map<Integer, CartRequest.UpdateDTO> updateDTOMap) {
        for (Iterator<Cart> iterator = cartList.iterator(); iterator.hasNext();) {
            Cart cart = iterator.next();
            CartRequest.UpdateDTO updateDTO = updateDTOMap.get(cart.getId());

            if (updateDTO == null) continue;

            if (updateDTO.getQuantity() == 0) {
                iterator.remove();
                cartJPARepository.delete(cart);
                continue;
            }

            updateCartQuantity(cart, updateDTO.getQuantity(), cart.getOption().getPrice());
        }
    }

}
