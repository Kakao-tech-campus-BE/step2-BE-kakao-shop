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
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        // requestDTO에 동일한 옵션이 있을 경우
        long setSize = requestDTOs.stream().map(o -> o.getOptionId()).distinct().count();
        if (requestDTOs.size() != setSize) {
            throw new Exception400("요청된 데이터에 동일한 옵션이 존재합니다. ");
        }

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 이미 장바구니에 똑같은 상품 존재하면, 해당 개수만큼 증가
            Cart cart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).orElse(null);

            Option option = optionJPARepository.findById(optionId).orElseThrow(
                    () -> new Exception404("해당 옵션을 찾을 수 없습니다 : "+optionId)
            );

            if (cart == null) { // 새로운 상품일때
                int price = option.getPrice()*quantity;
                Cart addCart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(price).build();
                cartJPARepository.save(addCart);
            } else { // 이미 장바구니에 존재해서 수량만 증가
                int updateQuantity = cart.getQuantity()+quantity;
                int updatePrice = option.getPrice()*updateQuantity;
                cart.update(updateQuantity, updatePrice);
            }
        }
    }

    @Transactional
    public CartResponse.FindAllDTO findAll(User user) {

        // cart -> option -> product
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {

        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다. ");
        }

        long setSize = requestDTOs.stream().map(cart -> cart.getCartId()).distinct().count();

        if (setSize == 0) {     // 요청된 데이터가 없을 때
            throw new Exception400("요청된 데이터가 없습니다. ");
        } else if (requestDTOs.size() != setSize) {     // 동일한 장바구니 번호 들어왔을 때
            throw new Exception400("요청된 데이터에 동일한 장바구니 번호가 존재합니다. ");
        }

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            int quantity = requestDTO.getQuantity();
            if (quantity<=0) {
                throw new Exception400("0 또는 음수 수량이 들어왔습니다. ");
            }

            Cart cart = cartList.stream()
                    .filter(c -> c.getId()==cartId).findFirst().orElseThrow(
                            () -> new Exception404("해당 장바구니를 찾을 수 없습니다. ")
                    );

            cart.update(quantity, cart.getOption().getPrice()*quantity);
        }

        return new CartResponse.UpdateDTO(cartList);
    }
}
