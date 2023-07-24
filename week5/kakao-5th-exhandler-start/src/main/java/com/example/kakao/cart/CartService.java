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

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // TODO: 1. 동일한 옵션이 들어오면 예외처리
        //  [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]

        // TODO: 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        //  { cartId:1, optionId:1, quantity:3, userId: 1 } -> [ { optionId:1, quantity:5 } ]
        //  save가 아닌 update 시키기

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
            cartJPARepository.save(cart);
        }

    }
}
