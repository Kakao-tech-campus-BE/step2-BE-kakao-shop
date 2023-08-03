package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final OptionJPARepository optionJPARepository;

    private final CartJPARepository cartJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]

        List<Integer> requestOptionIds = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .collect(Collectors.toList());

        checkDuplication(requestOptionIds);

        // 2. 이미 존재하면 장바구니에 수량을 추가하는 업데이트(더티체킹)
        //  { cartId:1, optionId:1, quantity:3, userId: 1 } -> [ { optionId:1, quantity:5 } ]

        List<Cart> needToUpdateCarts = cartJPARepository.findByOptionIdInAndUserId(requestOptionIds, sessionUser.getId());
        Map<Integer, Integer> requestMap = requestDTOs.stream()
                .collect(Collectors.toMap(
                        CartRequest.SaveDTO::getOptionId,
                        CartRequest.SaveDTO::getQuantity
                ));
        needToUpdateCarts.forEach(cart -> {
            int quantity = cart.getQuantity() + requestMap.get(cart.getOption().getId());
            cart.update(quantity, cart.getOption().getPrice() * quantity);
            requestMap.remove(cart.getOption().getId());
        });

        // 3. [2번이 아니라면] 유저의 장바구니에 담기(Map에서 제거한 것만 save)
        for (int optionId : requestMap.keySet()) {
            int quantity = requestMap.get(optionId);
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
            cartJPARepository.save(cart);
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) throw new Exception404("사용자의 장바구니가 존재하지 않습니다.");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<Integer> requestCartIds = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toList());

        checkDuplication(requestCartIds);

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        List<Integer> cartIds = cartList.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());

        requestDTOs.forEach(requestDTO -> {
            int requestCartId = requestDTO.getCartId();
            if (!cartIds.contains(requestCartId)) {
                throw new Exception400("장바구니 번호가 잘못되었습니다.:" + requestCartId);
            }
        });

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice()
                            * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹

    private void checkDuplication(List<?> list) {
        if (list.size() != list.stream().distinct().count()) {
            throw new Exception400("동일한 요청이 여러개 들어올 수 없습니다.");
        }
    }
}
