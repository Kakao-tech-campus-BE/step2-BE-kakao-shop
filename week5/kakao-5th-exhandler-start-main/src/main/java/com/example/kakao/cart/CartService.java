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
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;

    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        requestDTOs.forEach(requestDTO -> {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option option = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다. : "+optionId));
            int price = option.getPrice() * quantity;
            Cart cart = Cart.builder()
                    .user(sessionUser)
                    .option(option)
                    .quantity(quantity)
                    .price(price)
                    .build();
            cartJPARepository.save(cart);
        });
    }
}
