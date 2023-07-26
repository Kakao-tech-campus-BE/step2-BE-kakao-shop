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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartRepository;
    private final OptionJPARepository optionRepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        List<Integer> sets = requestDTOs.stream().map(CartRequest.SaveDTO::getOptionId).distinct().collect(Collectors.toList());
        if (sets.size() != requestDTOs.size()) {
            throw new Exception400("잘못된 요청입니다. 요청에서 중복이 발생하였습니다.");
        }

        List<Integer> optionIds = requestDTOs
                .stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .collect(Collectors.toList());

        List<Option> options = optionRepository.findAllById(optionIds);
        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId()).orElse(null);

        requestDTOs
                .forEach(request -> {
                    Cart cart = savedCarts
                            .stream()
                            .filter(cart1 -> cart1.getOption().getId() == request.getOptionId())
                            .findFirst()
                            .orElseGet(() -> {
                                Option option = options
                                        .stream()
                                        .filter(option1 -> option1.getId() == request.getOptionId())
                                        .findAny()
                                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다."));

                                return Cart.builder()
                                        .user(sessionUser)
                                        .option(option)
                                        .quantity(0)
                                        .price(0)
                                        .build();
                            });

                    int price = cart.getOption().getPrice();
                    int quantity = cart.getQuantity() + request.getQuantity();

                    cart.update(quantity, price * quantity);
                    cartRepository.save(cart);
                });
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId()).orElse(null);
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        if (requestDTOs.isEmpty()) {
            throw new Exception404("장바구니 업데이트 요청이 비어있습니다.");
        }

        List<Integer> sets = requestDTOs.stream().map(CartRequest.UpdateDTO::getCartId).distinct().collect(Collectors.toList());
        if (sets.size() != requestDTOs.size()) {
            throw new Exception400("잘못된 요청입니다. 요청에서 중복이 발생하였습니다.");
        }

        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId()).orElseThrow(
                () -> new Exception404("장바구니가 비어있습니다.")
        );
        List<Cart> carts = requestDTOs
                .stream()
                .map(request -> {
                    Cart cart = savedCarts
                            .stream()
                            .filter(cart1 -> cart1.getId() == request.getCartId())
                            .findAny()
                            .orElseThrow(() -> new Exception404("해당 장바구니를 찾을 수 없습니다."));

                    int quantity = request.getQuantity();
                    int price = quantity * cart.getOption().getPrice();
                    cart.update(quantity, price);

                    return cart;
                })
                .collect(Collectors.toList());

        cartRepository.saveAll(carts);
        return new CartResponse.UpdateDTO(savedCarts);
    } // 더티체킹
}
