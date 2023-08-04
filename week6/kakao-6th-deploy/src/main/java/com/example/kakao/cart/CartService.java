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

    @Transactional  // 종료될 때, 변경감지+더티체킹+flush+트랜잭션종료
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User user) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        Set<Integer> optionIdSet = new HashSet<>(); // 해시셋 생성
        Boolean hasSame = requestDTOs.stream()
                .map(requestDTO -> requestDTO.getOptionId())
                .anyMatch(optionId -> !optionIdSet.add(optionId));   // 해시셋에 추가했는데 안 되면(false) -> true를 하나라도 리턴한다면 곧 true가 됨(중복이있다)
        if (hasSame) {
            throw new Exception400("동일한 옵션이 입력되었습니다");
        }

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            // 2. 유효하지 않은 옵션이면 예외처리
            int optionId = requestDTO.getOptionId();
            Option optionPS = optionJPARepository.findById(optionId).orElseThrow(  // 쿼리문O
                    () -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId)
            );

            Cart cartPS = cartJPARepository.findByOptionIdAndUserId(optionId, user.getId());    // 쿼리문O
            // 3. 장바구니 담기
            if (cartPS == null) {
                int quantity = requestDTO.getQuantity();
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder()
                        .user(user)
                        .option(optionPS)
                        .quantity(quantity)
                        .price(price)
                        .build();
                cartJPARepository.save(cart);   // 쿼리문O
            }
            // 4. 이미 추가한 옵션이면 수량을 수정하기
            // cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            // Cart [ cartId:1, optionId:1, quantity:3, userId:1 ] -> DTO { optionId:1, quantity:5 } => quantity를 8로 만들기
            else {
                int quantity = cartPS.getQuantity() + requestDTO.getQuantity();
                int price = optionPS.getPrice() * quantity;
                cartPS.update(quantity, price); // 쿼리문O
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        // 1. 장바구니 조회
        List<Cart> cartListPS = cartJPARepository.mFindAllByUserIdOrderByOptionIdAsc(user.getId());   // 쿼리문O
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.

        return new CartResponse.FindAllDTO(cartListPS);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        // 1. 동일한 장바구니아이템이 들어오면 예외처리
        // cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> cartIdSet = new HashSet<>(); // 해시셋 생성
        Boolean hasSame = requestDTOs.stream()
                .map(requestDTO -> requestDTO.getCartId())
                .anyMatch(cartId -> !cartIdSet.add(cartId));   // 해시셋에 추가했는데 안 되면(false) -> true를 하나라도 리턴한다면 곧 true가 됨(중복이있다)
        if (hasSame) {
            throw new Exception400("동일한 장바구니아이템이 입력되었습니다");
        }

        List<Cart> cartListPS = cartJPARepository.mFindAllByUserId(user.getId());  // 쿼리문O

        // 2. 장바구니가 비어있으면 예외처리
        if (cartListPS.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다");
        }

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            // 3. 장바구니 수정
            int cartId = requestDTO.getCartId();
            Boolean flag = true;

            for (Cart cartPS : cartListPS) {
                if (cartId == cartPS.getId()) {

                    int quantity = requestDTO.getQuantity();
                    int price = cartPS.getOption().getPrice() * quantity;
                    cartPS.update(quantity, price); // 쿼리문O

                    flag = false;
                    break;
                }
            }
            // 4. 유효하지 않은 장바구니아이템이면 예외처리
            if (flag) {
                throw new Exception404("해당 장바구니아이템을 찾을 수 없습니다 : " + cartId);
            }
        }

        return new CartResponse.UpdateDTO(cartListPS);
    } // 더티체킹
}
