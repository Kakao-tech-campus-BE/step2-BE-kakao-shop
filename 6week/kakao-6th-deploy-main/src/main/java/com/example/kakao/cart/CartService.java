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
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        Set<Integer> idSet = new HashSet<>();
        requestDTOs.stream()
                .filter(c -> !idSet.add(c.getOptionId()))
                .findFirst()
                .ifPresent(c -> {
                    throw new Exception400("동일한 옵션이 들어왔습니다: " + c.getOptionId());
                });

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId())
                    .ifPresentOrElse(
                            c -> updateCart(c, quantity),
                            () -> {
                                int price = optionPS.getPrice() * quantity;
                                Cart cart = Cart.builder()
                                        .user(sessionUser)
                                        .option(optionPS)
                                        .quantity(quantity)
                                        .price(price)
                                        .build();
                                cartJPARepository.save(cart);
                            }
                    );
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTOv2(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if(requestDTOs.isEmpty()){
            throw new Exception404("장바구니가 비었습니다");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> idSet = new HashSet<>();
        requestDTOs.stream()
                .filter(c -> !idSet.add(c.getCartId()))
                .findFirst()
                .ifPresent(c -> {
                    throw new Exception400("동일한 장바구니가 들어왔습니다: " + c.getCartId());
                });

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            boolean check = true;
            for (Cart cart : cartList) {
                if (cart.getId() == updateDTO.getCartId()) {
                    int quantity = updateDTO.getQuantity();
                    cart.update(quantity, calcPrice(cart, quantity));

                    // 수량이 0이면 장바구니 삭제
                    if(quantity == 0){
                        cartJPARepository.delete(cart);
                    }
                    check = false;
                    break;
                }
            }
            if(check){
                throw new Exception404("해당 장바구니를 찾을 수 없습니다: " + updateDTO.getCartId());
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹

    public void updateCart(Cart cart, int quantity){
        cart.update(cart.getQuantity() + quantity, cart.getPrice() + calcPrice(cart, quantity));
    }

    public int calcPrice(Cart cart, int quantity){
        return cart.getOption().getPrice() * quantity;
    }
}
