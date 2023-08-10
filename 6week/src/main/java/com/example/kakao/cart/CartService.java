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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.AddDTO> requestDTOs, User sessionUser) {
        HashSet<Integer> cartSet = new HashSet<>();
        for (CartRequest.AddDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            if (cartSet.contains(optionId)) {
                throw new Exception400("중복되는 옵션이 존재합니다.:" + optionId);
            } else {
                cartSet.add(optionId);
            }
        }
        for (CartRequest.AddDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Optional<Cart> cartOP = cartJPARepository.findByUserIdAndOptionId(sessionUser.getId(), optionId);
            if (cartOP.isPresent()) {
                Cart cart = cartOP.get();
                int updateQuantity = quantity + cart.getQuantity();
                int updatePrice = cart.getOption().getPrice() * updateQuantity;
                cart.update(updateQuantity, updatePrice);
            } else {
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다.:" + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User sessionUser) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId());
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO updateCartList(List<CartRequest.UpdateDTO> requestDTOs, User sessionUser) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다.");
        }

        HashSet<Integer> cartSet = new HashSet<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            if (cartSet.contains(cartId)) {
                throw new Exception400("동일한 장바구니 아이디를 주문할 수 없습니다.:" + cartId);
            } else if (cartList.stream().noneMatch(cart -> cart.getId() == cartId)) {
                throw new Exception400("장바구니에 없는 상품은 주문할 수 없습니다.:" + cartId);
            } else {
                cartSet.add(cartId);
            }
        }

        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
                if (cart.getId() == requestDTO.getCartId()) {
                    cart.update(requestDTO.getQuantity(), cart.getOption().getPrice() * requestDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    }
}
