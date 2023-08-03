package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        int actualCount = (int) requestDTOs.stream()
                .mapToInt(requestDTO -> requestDTO.getOptionId()).distinct().count();

        if (requestDTOs.size() != actualCount)
            throw new Exception400("동일한 옵션을 담을 수 없습니다.");

        List<Cart> carts = new ArrayList<>();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            if (!cartOP.isEmpty()) {
                Cart cart = cartOP.get();
                int optionPrice = cart.getOption().getPrice();
                int totalQuantity = cart.getQuantity() + requestDTO.getQuantity();
                cart.update(totalQuantity, optionPrice * totalQuantity);
                continue;
            }

            // 3. [2번이 아니라면] 유저의 장바구니에 담기
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder()
                    .user(sessionUser)
                    .option(optionPS)
                    .quantity(quantity)
                    .price(price).build();

            carts.add(cart);
        }

        try { cartJPARepository.saveAllAndFlush(carts);
        } catch (Exception e) {
            throw new Exception500("상품을 담는 과정에서 오류가 발생했습니다.");
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

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty())
            throw new Exception400("장바구니가 비어 있습니다.");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        int count = requestDTOs.size();
        int actualCount = (int) requestDTOs.stream()
                .mapToInt(requestDTO -> requestDTO.getCartId())
                .distinct().count();

        if (count != actualCount)
            throw new Exception400("동일한 장바구니 상품을 수정할 수 없습니다.");


        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        Set<Integer> cartSet = cartList.stream()
                .mapToInt(cart -> cart.getId())
                .boxed()
                .collect(Collectors.toSet());

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            if (!cartList.stream().anyMatch(cart -> cart.getId() == cartId))
                throw new Exception404("존재하지 않는 장바구니 상품입니다 : " + requestDTO.getCartId());
        }


        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
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
