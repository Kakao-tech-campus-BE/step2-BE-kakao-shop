package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        // TODO - 과제
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList == null || cartList.isEmpty()) {
            throw new Exception404("장바구니가 비었습니다.");
        }


        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> checkList = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toSet());
        if (checkList.size() != requestDTOs.size()) {
            throw new Exception404("장바구니에 중복된 id가 존재합니다.");
        }


        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        boolean duplicateId = requestDTOs.stream().anyMatch((updateDTO) -> cartJPARepository.findById(updateDTO.getCartId()).isEmpty());
        if (duplicateId) {
            throw new Exception404("잘못된 cartId를 입력하셨습니다.");
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

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        // 1. 동일한 옵션이 들어오면 예외처리
        // TODO 과제
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        Set<Integer> checkList = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .collect(Collectors.toSet());
        if (checkList.size() != requestDTOs.size()) {
            throw new Exception404("장바구니에 중복된 id가 존재합니다.");
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // TODO 과제
        // Cart[ cardId:1, optionId:1, quantity:3, userId:1] -> DTO { optionId:1, quantity:5 }
        // 조회해서 더티체킹하기
        for (CartRequest.SaveDTO saveDTO : requestDTOs) {
            Optional<Cart> cart = cartJPARepository.findByOptionIdAndUserId(saveDTO.getOptionId(), sessionUser.getId());
            if (cart.isEmpty()) {
                continue;
            }
            Cart cart1 = cart.get();
            int quantity = cart.get().getQuantity() + saveDTO.getQuantity();
            int price = quantity * (cart.get().getPrice());
            cart1.update(quantity, price);
            return;
        }


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
    }// 변경감지, 더티체킹, flush, 트랜잭션 종료

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }
}
