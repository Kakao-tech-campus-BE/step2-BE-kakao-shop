package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    public void addCart(CartRequest.SaveDTO requestDto, CustomUserDetails userDetails){
        Option option = optionJPARepository.findById(requestDto.getOptionId()).orElseThrow(RuntimeException::new);
        Cart cart = Cart.builder()
                .user(userDetails.getUser())
                .option(option)
                .quantity(requestDto.getQuantity())
                .price(requestDto.getQuantity() * option.getPrice())
                .build();

        try {
            cartJPARepository.save(cart);
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }

    public List<Cart> checkCart(CustomUserDetails userDetails){
        List<Cart> cartList;

        try {
            cartList = cartJPARepository.mFindAllByUserId(userDetails.getUser().getId());
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }

        return cartList;
    }

    @Transactional
    public void updateCart(CartRequest.UpdateDTO updateDTO){
        Cart cartPS = cartJPARepository.findById(updateDTO.getCartId()).orElseThrow(
                () -> new Exception400("장바구니를 찾을 수 없습니다")
        );

        cartPS.update(updateDTO.getQuantity(), updateDTO.getQuantity() * cartPS.getOption().getPrice());
    }

    @Transactional
    public void deleteCart(CustomUserDetails userDetails){
        try {
            List<Cart> carts = cartJPARepository.mFindAllByUserId(userDetails.getUser().getId());
            List<Integer> cartIds = carts.stream()
                    .map(Cart::getId)
                    .collect(Collectors.toList());

            cartJPARepository.deleteAllById(cartIds);
        }catch (Exception e){
            throw new Exception500("unknown server error");
        }
    }
}
