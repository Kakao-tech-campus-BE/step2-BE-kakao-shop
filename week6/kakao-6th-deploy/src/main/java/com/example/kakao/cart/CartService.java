package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if(cartList.isEmpty())
            throw new Exception404("장바구니에 아무것도 존재하지 않습니다.");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<Integer> idList = new ArrayList<>();
        for(CartRequest.UpdateDTO requestDTO : requestDTOs){
            int cartId = requestDTO.getCartId();
            if(idList.contains(cartId))
                throw new Exception400("동일한 cartId를 요청했습니다. " + cartId);
            idList.add(cartId);
        }

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        List<Integer> cartIdList = new ArrayList<>();
        for (Cart cart : cartList) {
            cartIdList.add(cart.getId());
        }
        for(CartRequest.UpdateDTO updateDTO : requestDTOs){
            if(!cartIdList.contains(updateDTO.getCartId()))
                throw new Exception400("해당하는 cartId가 존재하지 않습니다. : " + updateDTO.getCartId());
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
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        List<Integer> idList = new ArrayList<>();
        for(CartRequest.SaveDTO requestDTO : requestDTOs){
            int optionId = requestDTO.getOptionId();
            if(idList.contains(optionId)){
                throw new Exception400("동일한 옵션이 들어왔습니다. : " + optionId);
            }
            idList.add(optionId);
        }

        // 조회 userId:1, optionId:1
        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // [ cartId:1, optionId:1, quantity:3, userId:1] - > DTO { optionId:1, quantity:5 }
        for(CartRequest.SaveDTO requestDTO : requestDTOs){
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            // 2번이라면 유저 장바구니 업데이트
            Optional<Cart> optionalCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            if(optionalCart.isPresent()){
                int cartId = optionalCart.get().getId();
                cartJPARepository.mUpdate(quantity, cartId);
            }
            // 2번이 아니라면 유저의 장바구니에 담기
            else{
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }


        // 3. [2번이 아니라면] 유저의 장바구니에 담기


    }


}
