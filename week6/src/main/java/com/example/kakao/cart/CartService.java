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
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional //트랜잭션 시작
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        HashSet<Integer> set = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            if (set.contains(optionId)) //중복 확인
                throw new Exception400("동일한 옵션 여러개를 추가할 수 없습니다.");
            else set.add(optionId);

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            // Cart {cartId:1, optionId:1, quantity:3, userId:1} -> DTO {optionId:1, quantity:5}
            Optional<Cart> optional = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            Option option = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception400("해당 옵션을 찾을 수 없습니다 : " + optionId));

            if(optional.isPresent()){ //이미 장바구니에 담긴 옵션이라면
                Cart cart = optional.get();
                int updateQuantity = cart.getQuantity() +quantity;
                int price = quantity * option.getPrice();
                cart.update(updateQuantity, price); //더티체킹 수행
            }
            // 3. [2번이 아니라면] 유저의 장바구니에 담기
            else{
                int price = option.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }

    } //변경감지, 더티체킹, flush, 트랜잭션 종료

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId()); //쿼리 수정
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    //    public CartResponse.FindAllDTOv2 findAllv2(User user) {
//        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
//        return new CartResponse.FindAllDTOv2(cartList);
//    }
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdWithOption(user.getId()); //쿼리 수정
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("담은 장바구니가 없습니다.");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        HashSet<Integer> set = new HashSet<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            if (set.contains(cartId)) //중복 확인
                throw new Exception400("동일한 장바구니를 동시에 update할 수 없습니다.");
            else set.add(cartId);
            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            boolean b = cartList.stream().anyMatch(cart -> cart.getId() == cartId);
            if(!b) throw new Exception404("존재하지않는 cartId입니다.");
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
