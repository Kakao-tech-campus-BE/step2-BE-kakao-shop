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
        // 1. 동일한 옵션이 들어오면 예외처리
        // [{ "optionId":1, "quantity":5 }, { "optionId":1, "quantity":10 } ]
        if (checkSameOptionIdExistence(requestDTOs)) {
            throw new Exception400("동일한 옵션이 입력되었습니다.");
        }
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Optional<Cart> updatingCart = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // [{ "optionId":1, "quantity":5 }] -> [{ "optionId":1, "quantity":5 }] 같은 optionId로 post하면 수량을 업데이트(quantity:10으로)하는 로직입니다.
            if (!updatingCart.isEmpty()) {
                int updatingQuantity = updatingCart.get().getQuantity();
                int requestQuantity = requestDTO.getQuantity();
                int updatingOptionPrice = updatingCart.get().getOption().getPrice();
                updatingCart.get().update(updatingQuantity + requestQuantity, updatingQuantity * updatingOptionPrice);
            }
        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        // [{ "optionId":1, "quantity":5 }, {"optionId":2, "quantity":10}]에서 {"optionId":2, "quantity":10}만 저장하는 로직입니다.
            else {
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    private boolean checkSameOptionIdExistence(List<CartRequest.SaveDTO> requestDTOs) {
        Set<Integer> optionIds = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            if (!optionIds.add(requestDTO.getOptionId()))
                return true;
        }
        return false;
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
        if(cartList.size() == 0)
            throw new Exception400("장바구니가 비어있습니다.");


        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        if(checkSameCartIdExistence(requestDTOs))
            throw new Exception400("동일한 장바구니 아이디 값이 입력되었습니다.");


        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for (Cart cart : cartList) {
            Set<Integer> cartIds = new HashSet<>();
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cartIds.add(cart.getId());
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
            if(cartIds.size() == 0)
                throw new Exception400("장바구니에 없는 아이디 값이 입력되었습니다.");
        }
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹

    private boolean checkSameCartIdExistence(List<CartRequest.UpdateDTO> requestDTOs) {
        Set<Integer> cartIds = new HashSet<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            if(!cartIds.add(requestDTO.getCartId())){
                return true;
            }
        }
        return false;
    }
}
