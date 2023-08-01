package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
        // 스트림, 가공
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        // 해당 유저가 들고있는 모든 카트 받기

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception400("장바구니가 비어있습니다.");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> cartIds = new HashSet<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            if (cartIds.contains(cartId)) {
                throw new Exception400("장바구니 아이디가 중복되었습니다. : " + cartId);
            }
            cartIds.add(cartId);
        }

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            boolean cartExists = false;
            for (Cart cart : cartList) {
                if (cart.getId() == cartId) {
                    cartExists = true;
                    break;
                }
            }
            if (!cartExists) {
                throw new Exception400("장바구니에 없는 cartId가 들어왔습니다. : " + cartId);
            }
        }

        // 위 3개 잡는 것이 숙제다

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        } // for문 돌리면서 객체 상태만 바꿔주면 된다

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹

    // transactional 걸면 건것이 동작한다
    // addcartlist 시작하기 전에 transactional시작하고 
    // 종료될 때 transactional 끝남
    @Transactional
    // savedto가 optionid, quantity를 들고있는데 collection으로 받음
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]

        Set<Integer> optionIdSet = new HashSet<>();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            if (optionIdSet.contains(optionId)) {
                throw new Exception400("동일한 옵션이 들어왔습니다. : " + optionId);
            }
            optionIdSet.add(optionId);
        }

        // 조회 userId:1, optionId:1
        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // Cart [ cartId:1, optionId:1, quantity:3, userID:1] -> DTO { optionId:1, quantity:5 }

        // 3번에 같이 구현 하였습니다

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            // 옵션 검증 500번이 들어오면 save하면 안되니까
            // optionPS가 있어야 가격을 알 수 있음
            // 옵션id로 optionPS가 존재하는지도 확인할 겸 조회해서 확인하면 됨

            Optional<Cart> existingCartOptional = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            if (existingCartOptional.isPresent()) {
                // 이미 해당 옵션 ID를 가진 장바구니가 존재하면 수량을 업데이트
                Cart existingCart = existingCartOptional.get();
                int newQuantity = existingCart.getQuantity() + quantity;
                int newPrice = existingCart.getOption().getPrice() * newQuantity;
                existingCart.update(newQuantity, newPrice);
            } else {
                // 3. [2번이 아니라면] 유저의 장바구니에 담기 (없다면)
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
            // jpa에서 save할 때 enetity를 넘겨야되니까 cart에 옵션 객체를 넣음

            // optionId:1, quantityL5, price:50000 을 받았다고 생각하자
            // 이건 신뢰할 수 없음

        } // 변경감지, 더티체킹, flush, 트랜잭션 종료
    }
}
