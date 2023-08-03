package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User user) {

        //1. requestDTOs에 동일한 옵션 아이디가 존재할 경우
        boolean isDuplicated = requestDTOs.stream()
                .mapToInt(CartRequest.SaveDTO::getOptionId)
                .distinct()
                .count() < requestDTOs.size();
        if(isDuplicated){
            throw new Exception400("입력 중 동일한 옵션 아이디가 존재합니다.");
        }

        //2. 카트가 존재할 경우 update, 존재하지 않을 경우 save
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            Option option = optionJPARepository.findById(optionId).orElseThrow(
                    () -> new Exception404("해당 옵션을 찾을 수 없습니다.")
            );
            Optional<Cart> findCart = cartJPARepository.findByOptionIdAndUserId(optionId, user.getId());

            if(findCart.isPresent()){
                Cart cart = findCart.get();
                int updateQuantity = cart.getQuantity() + quantity;
                int updatePrice = option.getPrice() * updateQuantity;
                cart.update(updateQuantity, updatePrice);
                cartJPARepository.update(cart);
            }else{
                int price = option.getPrice() * quantity;
                Cart cart = Cart.builder().user(user).option(option).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> carts = cartJPARepository.findAllByUserIdJoinFetch(user.getId());
        return new CartResponse.FindAllDTO(carts);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> carts = cartJPARepository.findAllByUserIdJoinFetch(user.getId());

        //1. 사용자 장바구니가 비어있을 경우
        if(carts.isEmpty()){
            throw new Exception400("사용자의 장바구니가 비어있습니다.");
        }

        //2. requestDTOs에 동일한 장바구니 아이디가 존재할 경우
        boolean isDuplicated = requestDTOs.stream()
                .mapToInt(CartRequest.UpdateDTO::getCartId)
                .distinct()
                .count() < requestDTOs.size();
        if(isDuplicated){
            throw new Exception400("입력 중 동일한 장바구니 아이디가 존재합니다.");
        }

        //3. 사용자 장바구니에 있는 cartId가 들어온 경우 update
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            int quantity = requestDTO.getQuantity();

            Cart cart = carts.stream().filter(c -> c.getId() == cartId).findFirst().orElseThrow(
                    () -> new Exception404("해당 카트를 찾을 수 없습니다.")
            );
            cart.update(quantity, cart.getOption().getPrice() * quantity);
            cartJPARepository.update(cart);
        }

        return new CartResponse.UpdateDTO(carts);
    }
}
