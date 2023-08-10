package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        //동일한 옵션이 한번에 들어오면 예외처리'

        System.out.println("abcabc");
        userJPARepository.findAll().forEach(System.out::println);
        List<Integer> optionIdList = requestDTOs.stream().map(CartRequest.SaveDTO::getOptionId).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        for (int i = 0; i < optionIdList.size() - 1; i++) {
            if (Objects.equals(optionIdList.get(i), optionIdList.get(i + 1))) {
                throw new Exception400("같은 장바구니에 동일한 옵션이 존재합니다 : " + optionIdList.get(i));
            }
        }
        //기존 카트에 requestDTOs의 optionId가 있을 시 개수만 업데이트

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId).orElseThrow(
                    () -> new Exception400("해당 옵션을 찾을 수 없습니다 : " + optionId)
            );
            if (cartJPARepository.existsByOptionIdAndUserId(optionPS.getId(), sessionUser.getId())) {
                Cart alreadyExistCart = cartJPARepository.findByOptionIdAndUserId(optionPS.getId(), sessionUser.getId()).orElseThrow(
                        () -> new Exception400("오류 발생")
                );
                int alreadyExistCartQuantity = alreadyExistCart.getQuantity();
                int addedQuantity = quantity + alreadyExistCartQuantity;
                int price = optionPS.getPrice() * (addedQuantity);

                alreadyExistCart.update(addedQuantity, price);
                cartJPARepository.save(alreadyExistCart);
            } else {
                int price = optionPS.getPrice() * quantity;
                Cart cart =
                        Cart.builder()
                                .user(sessionUser)
                                .option(optionPS)
                                .quantity(quantity)
                                .price(price)
                                .build();
                cartJPARepository.save(cart);
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        if(cartList.size() == 0)
            throw new Exception400("장바구니가 비어있습니다");//1번 예외처리 완료

        if(requestDTOs.stream().map(CartRequest.UpdateDTO::getCartId).distinct().count() != requestDTOs.size())
            throw new Exception400("장바구니 항목 중복 발생 오류");//2번 예외 처리 완료
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
