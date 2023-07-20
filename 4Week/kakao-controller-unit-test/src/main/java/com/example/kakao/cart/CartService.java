package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.utils.ApiUtils;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
