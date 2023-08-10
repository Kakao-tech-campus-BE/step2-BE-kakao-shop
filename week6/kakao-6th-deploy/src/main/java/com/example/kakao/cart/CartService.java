package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
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
        if (requestDTOs.size() != requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .distinct()
                .count()){
            throw new Exception404("동일한 옵션아이디가 요청되었습니다 ");
        }

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            int userId = sessionUser.getId();

            Cart cart = cartJPARepository.findByOptionIdAndUserId(optionId, userId).orElse(null);
            if (cart != null){
                // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(()->new Exception404("해당 옵션을 찾을 수 없습니다 :"+optionId));
                cart.update(cart.getQuantity() + quantity, cart.getPrice() + optionPS.getPrice() * quantity);
            }else {
                // 3. [2번이 아니라면] 유저의 장바구니에 담기
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 :" + optionId));
                int price = optionPS.getPrice() * quantity;
                cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price)
                        .build();
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
        if (cartList.isEmpty()){
            throw new Exception404("장바구니가 비어있습니다 ");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        if (requestDTOs.size() != requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .distinct()
                .count()){
            throw new Exception404("동일한 카트아이디가 요청되었습니다 ");
        }

        for (CartRequest.UpdateDTO updateDTO : requestDTOs){
            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리 장바구니에 담겨 있는 데이터 아래와 같이 요청이 되면 예외가 나와야 한다.
            if (cartList.stream().noneMatch((c) -> c.getId() == updateDTO.getCartId())){
                throw new Exception404("해당 카트아이템을 찾을 수 없습니다 :" + updateDTO.getCartId());
            }
            // 4. 장바구니 수정
            for (Cart cart : cartList){
                if (cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    }
}