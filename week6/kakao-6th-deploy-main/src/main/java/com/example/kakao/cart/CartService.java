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

        Set<Integer> checkOptionId = new HashSet<>();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            if(!checkOptionId.add(optionId)) {
                throw new Exception400("동일한 상품이 중복으로 담겨있습니다. : " + optionId);
            }

            if(quantity < 1) {
                throw new Exception400("최소 1개 이상의 상품을 담아주시기 바랍니다. : " + optionId);
            }

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception400("해당 상품을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;

            Optional<Cart> findExistingCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            if(findExistingCart.isPresent()) {
                Cart existingCart = findExistingCart.get();
                existingCart.update(existingCart.getQuantity() + quantity, existingCart.getPrice() + price);
            } else {
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());

        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.mFindAllByUserId(user.getId());

        if(cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        Set<Integer> checkCartId = new HashSet<>();

        for(CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            int quantity = updateDTO.getQuantity();

            if (!checkCartId.add(cartId)) {
                throw new Exception400("동일한 상품이 중복으로 담겨있습니다 : " + cartId);
            }

            if(quantity < 1) {
                throw new Exception400("최소 1개 이상의 상품을 담아주시기 바랍니다. : " + cartId);
            }

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
