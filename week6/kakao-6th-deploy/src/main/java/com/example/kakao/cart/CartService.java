package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        Set<Integer> optionIds = new HashSet<>();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();

            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ] -> 에러 발생
            // 이미 처리한 옵션 아이디인 경우 예외 처리
            if (optionIds.contains(optionId)) {
                throw new Exception400("동일한 옵션을 선택할 수 없습니다.");
            }
            optionIds.add(optionId);

            int quantity = requestDTO.getQuantity();

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            // 장바구니에 해당 물품이 이미 있는지 확인
            Cart cartItem = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).orElse(null);

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            if (cartItem != null) {
                cartItem.update(cartItem.getQuantity() + quantity, cartItem.getPrice() + optionPS.getPrice() * quantity);
            } else { // 3. [2번이 아니라면] 유저의 장바구니에 담기
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    } // 변경 감지, 더티체킹, flush, 트랜잭션 종료

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception400("장바구니가 비었습니다.");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> cartIds = new HashSet<>();

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();

            // 동일한 장바구니 아이디가 들어올 경우
            if (cartIds.contains(cartId)) {
                throw new Exception400("동일한 장바구니 요청입니다.");
            }
            cartIds.add(cartId);

            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            boolean cartIdFound = false;
            for (Cart cart : cartList) {
                if (cart.getId() == cartId) {
                    cartIdFound = true;
                    break;
                }
            }
            if (!cartIdFound) {
                throw new Exception404("유효하지 않은 장바구니입니다.");
            }
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
    // 장바구니에 담긴 상품을 삭제하는 부분도 작성하면 좋을 것 같다.션
}