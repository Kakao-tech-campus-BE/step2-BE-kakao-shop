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
            int optionId = requestDTO.getOptionId();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            Cart cartPS = cartJPARepository.findByOptionIdAndUserId(optionId, user.getId());

            // 2. 유저의 장바구니에 담기
            if (cartPS == null) {
                int quantity = requestDTO.getQuantity();
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(user).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
            // 3. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            // Cart [ cartId:1, optionId:1, quantity:3, userId:1 ] -> DTO { optionId:1, quantity:5 } => quantity를 8로 만들기
            else {
                int quantity = cartPS.getQuantity() + requestDTO.getQuantity();
                int price = optionPS.getPrice() * quantity;
                cartPS.update(quantity, price);
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

        // 1. 유저 장바구니에 아무것도 없으면 예외처리

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리

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
