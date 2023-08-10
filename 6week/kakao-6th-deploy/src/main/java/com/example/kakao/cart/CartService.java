package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        // 이 경우 최대한 필터링으로 걸러내는 것이 좋음 (과제)

        // hashset으로 중복 제거
        Set<Integer> processedOptionId = new HashSet<>();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            if (!processedOptionId.add(optionId)) {
                throw new Exception400("동일한 옵션이 이미 담겨있습니다. : " + optionId);
            }

            Cart existingCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId())
                .orElse(null);

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            // 조회 userId : 1, optionId : 1 -> 조회하고 더티체킹해서 update

            if (existingCart != null) {
                existingCart.update(existingCart.getQuantity() + quantity,
                    existingCart.getPrice() * quantity);
            } else {
                Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity)
                    .price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }


    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리 (과제)
        if (cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리 (과제)
        Set<Long> processedCartId = new HashSet<>();

        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            long cartId = updateDTO.getCartId();
            if (!processedCartId.add(cartId)) {
                throw new Exception400("동일한 상품이 이미 담겨있습니다. : " + cartId);
            }
        }

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리 (과제)
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            long cartId = updateDTO.getCartId();
            boolean existingCart = cartList.stream().anyMatch(cart -> cart.getId() == cartId);
            if (!existingCart) {
                throw new Exception400("유저의 장바구니에 없는 상품 입니다.");
            }
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(),
                        cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
