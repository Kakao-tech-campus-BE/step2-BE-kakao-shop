package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
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
        Set<Integer> checkOptionId = new HashSet<>();

        for(CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            validateDuplicatedId(optionId, checkOptionId);
            validateQuantity(optionId, quantity);

            saveOrUpdateCart(sessionUser, optionId, quantity);
        }
    }

    private void saveOrUpdateCart(User sessionUser, int optionId, int quantity) {
        Option option = validateExistOption(optionId);
        cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId())
                .ifPresent(
                        existingCart -> existingCart.update(existingCart.getQuantity() + quantity, existingCart.getPrice() + option.getPrice() * quantity)
                );

        Cart cart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(option.getPrice() * quantity).build();
        cartJPARepository.save(cart);
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionId(user.getId());

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

            validateDuplicatedId(cartId, checkCartId);
            validateQuantity(cartId, quantity);

            updateCartIfExist(cartList, cartId, quantity);
        }

        return new CartResponse.UpdateDTO(cartList);
    }

    private static void updateCartIfExist(List<Cart> cartList, int cartId, int quantity) {
        Cart cart = cartList.stream()
                .filter(c -> c.getId() == cartId)
                .findFirst()
                .orElseThrow(() -> new Exception400("해당 상품은 장바구니에 존재하지 않습니다 : " + cartId));

        cart.update(quantity, cart.getOption().getPrice() * quantity);
    }

    private Option validateExistOption(int id) {
        return optionJPARepository.findById(id)
                .orElseThrow(() -> new Exception400("해당 상품을 찾을 수 없습니다 : " + id));
    }

    private void validateDuplicatedId(int id, Set<Integer> checkId) {
        if(checkId.contains(id)) {
            throw new Exception400("동일한 상품이 이미 담겨있습니다. : " + id);
        }

        checkId.add(id);
    }

    private void validateQuantity(int id, int quantity) {
        if(quantity < 1) {
            throw new Exception400("최소 1개 이상의 상품을 담아주시기 바랍니다. : " + id);
        }
    }
}
