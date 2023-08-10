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
        // Set으로 동일한 optionId 검증
        Set<Integer> optionIdSet = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            // 1. 동일한 옵션
            // [ { "optionId":1, "quantity":5 }, { "optionId":1, "quantity":10 } ]
            if(!optionIdSet.add(optionId)){
                throw new Exception400("동일한 상품이 장바구니에 있습니다." + optionId);
            }

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            int price = optionPS.getPrice() * quantity;

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            // 더티체킹은 조회기준
            Optional<Cart> existCartUpdate =  cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            if(existCartUpdate.isPresent()){
                Cart existCart = existCartUpdate.get();
                existCart.update(existCart.getQuantity()+quantity, existCart.getPrice()+price);
            }
            else{ // 3. 2번이 아니라면 유저의 장바구니
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
//            강의 다시 들으면서 추가한 코드, price를 신뢰할 수 없음 price가 DB에서 검증이 안됨, 검증하면 괜찮음
//            for(CartRequest.SaveDTO requestDTO : requestDTOs){
//                int optionId = requestDTO.getOptionId();
//                int quantity = requestDTO.getQuantity();
//                int price = requestDTO.getPrice();
//                cartJPARepository.mSave(sessionUser.getId(), optionId,quantity,price);
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
        if(cartList.isEmpty()){
            throw new Exception400("장바구니에 아무것도 없습니다.");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> cartIdSet = new HashSet<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            if (!cartIdSet.add(cartId)){
                throw new Exception400("장바구니에 동일한 상품이 있습니다.");
            }

            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            for (Cart cart : cartList) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}