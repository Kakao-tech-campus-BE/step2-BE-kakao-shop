package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception403;
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
        List<CartRequest.SaveDTO> sets = requestDTOs.stream().distinct().collect(Collectors.toList());

        if (sets.size() != requestDTOs.size()) {
            throw new Exception400("잘못된 요청입니다. 요청에서 중복이 발생하였습니다.");
        }

        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)

        // 3. [2번이 아니라면] 유저의 장바구니에 담기

        List<Cart> savedCarts = cartRepository.findByUserIdOrderByOptionIdAsc(sessionUser.getId());

        requestDTOs
                .forEach(request -> {
                    // 앞서 조회한 카트 리스트에서 요청에 맞는 옵션을 발견하면 해당 카트를 선택
                    // 찾지 못하면 요청에 맞는 새로운 카트를 생성
                    Cart cart = savedCarts
                            .stream()
                            .filter(cart1 -> cart1.getOption().getId() == request.getOptionId())
                            .findFirst()
                            .orElseGet(() -> {
                                Option option = optionRepository.findById(request.getOptionId()).orElseThrow(
                                        () -> new Exception404("해당 옵션을 찾을 수 없습니다.")
                                );

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
        List<Cart> cartList = cartRepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public List<Cart> findAllByUserInRaw(User user) {
        return cartRepository.findAllByUserIdOOrderByOptionIdAsc(user.getId());
    }

    public void save(Cart cart) {
        try {
            cartRepository.save(cart);
        }
        catch (Exception exception) {
            throw new Exception403("이미 장바구니에 존재하는 상품입니다.");
        }
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        List<Cart> carts = requestDTOs
                .stream()
                .map(request -> {
                    Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new Exception404("해당 장바구니 아이템을 찾을 수 없습니다."));

                    if (cart.getUser().getId() != user.getId()) {
                        throw new Exception403("현재 계정으로 해당 장바구니를 업데이트 할 수 없습니다.");
                    }

                    int quantity = request.getQuantity();
                    int price = quantity * cart.getOption().getPrice();
                    cart.update(quantity, price);

                    return cart;
                })
                .collect(Collectors.toList());

        cartRepository.saveAll(carts);
        List<Cart> savedCarts = cartRepository.findAllByUserId(user.getId());
        return new CartResponse.UpdateDTO(savedCarts);
    } // 더티체킹
}
