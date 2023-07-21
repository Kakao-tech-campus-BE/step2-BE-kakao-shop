package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserJPARepository userJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    public void saveCarts(List<CartRequest.SaveDTO> requestDTO, Integer userId) {

        List<Cart> carts = requestDTO.stream().map(saveDTO -> Cart.builder()
                .user(userJPARepository.findById(userId).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다.")))
                .option(optionJPARepository.findById(saveDTO.getOptionId()).orElseThrow(() -> new RuntimeException("옵션 정보를 찾을 수 없습니다.")))
                .quantity(saveDTO.getQuantity())
                .build()).collect(Collectors.toList());

        cartJPARepository.saveAll(carts);
    }

    public void deleteAll(User user) {
        cartJPARepository.deleteAllByUser(user);
    }
}
