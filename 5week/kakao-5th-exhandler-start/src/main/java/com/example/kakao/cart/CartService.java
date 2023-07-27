package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Map<Integer,Boolean> optionMap = new HashMap<>();
        for(CartRequest.SaveDTO saveDTO:requestDTOs){
            int optionId=saveDTO.getOptionId();
            if(optionMap.containsKey(optionId)){
                throw new Exception400("동일한 옵션이 선택되었습니다 : " + optionId);
            }
            else optionMap.put(optionId,true);
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // -> 3번 코드에 추가

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart;

            Optional<Cart> findCart = cartJPARepository.findByOptionIdAndUserId(optionId,sessionUser.getId());
            if(findCart.isEmpty()){
                cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
            else{
                cart = findCart.get();
                int updateQuantity = quantity + cart.getQuantity();
                cart.update(updateQuantity , optionPS.getPrice() * updateQuantity);
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
        if(requestDTOs.isEmpty()) throw new Exception400("장바구니가 비어있습니다.");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Map<Integer, Boolean> cartMap = new HashMap<>();
        for(CartRequest.UpdateDTO updateDTO : requestDTOs){
            int cartId=updateDTO.getCartId();
            if(cartMap.containsKey(cartId)){
                throw new Exception400("중복 상품 항목이 있습니다 : "+cartId);
            }
            else cartMap.put(cartId,true);
        }
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리 & 장바구니 수정
        for (Cart cart : cartList) {
            boolean matchCart=false;
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    matchCart=true;
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
            if(!matchCart) throw new Exception403("올바르지 않은 항목이 추가되었습니다");
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}