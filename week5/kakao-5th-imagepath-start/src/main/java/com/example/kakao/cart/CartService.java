package com.example.kakao.cart;

import com.example.kakao._core.errors.ErrorCode;
import com.example.kakao._core.errors.exception.CustomException;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        Set<Integer> optionIdSet = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            if (!optionIdSet.add(optionId)) {
                throw new CustomException(ErrorCode.DUPLICATE_CART_OPTIONS);
            }

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new CustomException(ErrorCode.OPTION_NOT_FOUND));

            Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            if (cartOP.isPresent()) {
                Cart cart = cartOP.get();
                cart.update(
                        cart.getQuantity() + quantity,
                        (cart.getQuantity() + quantity) * cart.getOption().getPrice()
                );
            } else { // 3. [2번이 아니라면] 유저의 장바구니에 담기
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
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

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            // throw new CartException.EmptyCartException(); <- CartException Class 활용
            throw new CustomException(ErrorCode.EMPTY_CART); // <- Enum 활용
        }

        Set<Integer> cartIdSet = new HashSet<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            int quantity = updateDTO.getQuantity();

            // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
            if (!cartIdSet.add(cartId)) {
                throw new CustomException(ErrorCode.DUPLICATE_CART_ITEMS);
            }

            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            Optional<Cart> cartOP = cartJPARepository.findById(cartId);
            if (cartOP.isEmpty()) {
                throw new CustomException(ErrorCode.CART_ITEM_NOT_FOUND);
            } else {
                Cart cart = cartOP.get();
                cart.update(quantity, quantity * cart.getOption().getPrice());
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}