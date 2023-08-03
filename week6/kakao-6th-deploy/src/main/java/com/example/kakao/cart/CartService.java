package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        List<Integer> optionIds = new ArrayList<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            if (!optionIds.contains(optionId)) {
                optionIds.add(optionId);
            } else {
                throw new Exception400("상품이 중복됩니다 :" + optionId);
            }

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;


            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            Optional<Cart> ExistCheckCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());

            if (ExistCheckCart.isPresent()) {
                Cart existingCart = ExistCheckCart.get();
                existingCart.update(existingCart.getQuantity() + quantity, existingCart.getPrice() + price);
            } else {        // 3. [2번이 아니라면] 유저의 장바구니에 담기
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }

        }


    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // 프론트엔드 코드가 장바구니가 비어있으면 에러가 아니라
        // 빈 배열로 응답하는 방식이라 에러 발생해서 주석 처리
        //if (cartList.isEmpty()) {
          //  throw new Exception404("장바구니에 담은 상품이 없습니다.");
        //}
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
            throw new Exception404("장바구니에 담은 상품이 없습니다.");
        }

        List<Integer> requestCartIds = new ArrayList<>();

        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {

            // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
            int cartId = updateDTO.getCartId();

            if (!requestCartIds.contains(cartId)) {
                requestCartIds.add(cartId);
            } else if (requestCartIds.contains(cartId)) {
                throw new Exception400("상품이 중복됩니다. :" + cartId);
            }

            int count = 0;
            for (Cart cart : cartList) {
                if (cart.getId() == cartId) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                    count++;
                }
            }

            if (count == 0) {
                throw new Exception404("장바구니에 없는 상품입니다. :" + cartId);
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    }// 더티체킹

}