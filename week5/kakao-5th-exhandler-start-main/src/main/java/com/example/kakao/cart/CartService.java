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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional // 메소드가 끝날 때 변경 감지, 더티 체킹, flush, 트랜잭션 종료
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

            Set<Integer> checkOptionId = new HashSet<>();

            for (CartRequest.SaveDTO requestDTO : requestDTOs) {
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();

                // 1. 동일한 옵션이 들어오면 예외처리
                // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
                if(!checkOptionId.add(optionId)) {
                    throw new Exception400("동일한 상품이 중복으로 담겨있습니다. : " + optionId);
                }

                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 상품을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;

                // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
                Optional<Cart> findExistingCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
                if(findExistingCart.isPresent()) {
                    Cart existingCart = findExistingCart.get();
                    existingCart.update(existingCart.getQuantity() + quantity, existingCart.getPrice() + price);
                } else {
                    // 3. [2번이 아니라면] 유저의 장바구니에 담기
                    Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                    cartJPARepository.save(cart);
                }
            }
        
        // 만약 모든 필드를 가져온다면 -> 위험한 방법 => 검증 필요
//        for(CartRequest.SaveDTO requestDTO : requestDTOs) {
//            int optionId = requestDTO.getOptionId();
//            int quantity = requestDTO.getQuantity();
//            int price = requestDTO.getPrice();
//
//            cartJPARepository.mSave(sessionUser.getId(), optionId, quantity, price);
//        }
    }

    // productDTO 중복 제거
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    // productDTo 중복 제거 X
    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

    // c join fetch option
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.mFindAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if(cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Set<Integer> checkCartId = new HashSet<>();

        for(CartRequest.UpdateDTO updateDTO : requestDTOs) {
            // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
            int cartId = updateDTO.getCartId();

            if (!checkCartId.add(cartId)) {
                throw new Exception400("동일한 상품이 중복으로 담겨있습니다 : " + cartId);
            }

            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            boolean cartExist = false;

            for(Cart cart : cartList) {
                if(cart.getId() == cartId) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                    cartExist = true;
                    break;
                }
            }

            if(!cartExist) {
                throw new Exception400("해당 상품은 장바구니에 존재하지 않습니다 : " + cartId);
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    }

    // join fetch X
    @Transactional
    public CartResponse.UpdateDTO updateV2(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if(cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Set<Integer> checkCartId = new HashSet<>();

        for(CartRequest.UpdateDTO updateDTO : requestDTOs) {
            // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
            int cartId = updateDTO.getCartId();

            if (!checkCartId.add(cartId)) {
                throw new Exception400("동일한 상품이 중복으로 담겨있습니다 : " + cartId);
            }

            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            boolean cartExist = false;

            for(Cart cart : cartList) {
                if(cart.getId() == cartId) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                    cartExist = true;
                    break;
                }
            }

            if(!cartExist) {
                throw new Exception400("해당 상품은 장바구니에 존재하지 않습니다 : " + cartId);
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    }
}
