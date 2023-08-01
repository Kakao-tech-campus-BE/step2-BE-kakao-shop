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


@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    //장바구니 조회기능
    @Transactional(readOnly = true)
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    //장바구니 담기 기능
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        Set<Integer> optionIds = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            // 1. 동일한 옵션이 들어오면 예외처리 [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            int optionId = requestDTO.getOptionId();
            if (!optionIds.add(optionId)) {
                throw new Exception400("동일한 옵션을 여러번 들어왔습니다.: " + optionId);
            }

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회
            Optional<Cart> cartPS = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());
            cartPS.ifPresentOrElse( cart -> {
                //DB에 동일한 옵션 ID가 존재하면 장바구니에 수량을 추가하는 업데이트
                int quantity = cart.getQuantity() + requestDTO.getQuantity();
                int price = quantity * cart.getOption().getPrice();
                cart.update(quantity, price);
                },
                    // 3. [2번이 아니라면] 유저의 장바구니에 담기
                    () -> {
                        int quantity = requestDTO.getQuantity();
                        Option optionPS = optionJPARepository.findById(optionId)
                                // 요청한 옵션 값이 존재하지 않으면 예외처리
                                .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                        int price = optionPS.getPrice() * quantity;
                        Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                        cartJPARepository.save(cart);
                    });
        }
    } // 변경감지, 더티체킹, flush, 트랜젝션 종료

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartsPS = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartsPS.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Set<Integer> cartPSIds = cartsPS.stream()
                .map(Cart::getId)
                .collect(Collectors.toSet());

        Set<Integer> cartIds = new HashSet<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();

            // requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리 => cartId:1, cartId:1
            if (!cartIds.add(cartId)) {
                throw new Exception400("동일한 장바구니 아이디가 두번 이상 들어왔습니다.");
            }

            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            if (!cartPSIds.contains(cartId)) {
                throw new Exception400("해당 장바구니 ID값은 존재하지 않습니다.");
            }
        }

        requestDTOs.forEach(requestDTO -> cartsPS.stream()
                .filter(cart -> cart.getId() == requestDTO.getCartId())
                .forEach(cart -> cart.update(requestDTO.getQuantity(),
                        cart.getOption().getPrice() * requestDTO.getQuantity())));

        return new CartResponse.UpdateDTO(cartsPS);
    } // 더티체킹
}
