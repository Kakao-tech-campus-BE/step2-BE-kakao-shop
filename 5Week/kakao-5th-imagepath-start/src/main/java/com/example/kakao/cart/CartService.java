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
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        throwSameOptionIdException(requestDTOs);

        for(CartRequest.SaveDTO requestDTO : requestDTOs){
            Cart cart = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());
            if(cart != null){
                cart.update(requestDTO.getOptionId(), requestDTO.getQuantity()+cart.getQuantity());
            }else {
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
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

        throwEmptyCartException(cartList);

        throwDuplicateCartIdException(requestDTOs);

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for(CartRequest.UpdateDTO updateDTO : requestDTOs){
            boolean found = false;
            for(Cart cart : cartList){
                if(updateDTO.getCartId() == cart.getId()){
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                    found = true;
                }
            }
            if(!found){
                throw new Exception400("해당 장바구니가 없습니다.");
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹

    private void throwSameOptionIdException(List<CartRequest.SaveDTO> requestDTOs){
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        Set<Integer> uniqueRequestDTOS = new HashSet<>();
        for(CartRequest.SaveDTO carts : requestDTOs){
            if(!uniqueRequestDTOS.add(carts.getOptionId())){
                throw new Exception400("동일한 옵션이 존재합니다.");
            }
        }
    }

    private void throwEmptyCartException(List<Cart> cartList){
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if(cartList.isEmpty()){
            throw new Exception400("장바구니에 상품이 없습니다.");
        }
    }

    private void throwDuplicateCartIdException(List<CartRequest.UpdateDTO> requestDTOs){
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> uniqueRequestDTOs = new HashSet<>();
        for(CartRequest.UpdateDTO requestDTO : requestDTOs){
            if(!uniqueRequestDTOs.add(requestDTO.getCartId())){
                throw new Exception400("동일한 장바구니 요청이 있습니다.");
            }
        }
    }
}
