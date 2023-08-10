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

    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;

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
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // optionId, quantity를 map으로 저장
        Map<Integer, Integer> map = new HashMap<>();

        for (CartRequest.SaveDTO saveDTO : requestDTOs) {
            int optionId = saveDTO.getOptionId();
            int quantity = saveDTO.getQuantity();

            if (quantity == 0) {
                throw new Exception400("옵션의 수량이 0입니다. 삭제 요청을 해주세요.");
            }

            // 1. 동일한 옵션이 들어오면 예외처리
            if (map.containsKey(optionId)) { // 이미 옵션이 저장되어 있을 경우 = 2개 이상 있을 경우
                throw new Exception400("요청에 같은 옵션이 여러 개입니다.");
            } else {
                map.put(optionId, quantity);
            }
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        for (Integer optionId : map.keySet()) {
            int quantity = map.get(optionId);

            // 장바구니 존재할 때 -> 업데이트
            if (cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).isPresent()) {
                Cart cart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId()).get();

                cart.update(cart.getQuantity() + quantity,
                        cart.getPrice() + quantity * cart.getOption().getPrice());
            } else {
                // 장바구니 존재하지 않을 때
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart newCart = Cart.builder()
                        .user(sessionUser)
                        .option(optionPS)
                        .quantity(quantity).
                        price(price)
                        .build();
                cartJPARepository.save(newCart);
            }
        }

//        // 3. [2번이 아니라면] 유저의 장바구니에 담기 -> 2번에 같이
//        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
//            int optionId = requestDTO.getOptionId();
//            int quantity = requestDTO.getQuantity();
//            Option optionPS = optionJPARepository.findById(optionId)
//                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
//            int price = optionPS.getPrice() * quantity;
//            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
//            cartJPARepository.save(cart);
//        }

    } // 변경 감지, 더티체킹, flush, 트랜잭션 종료

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        Set<Integer> set = new HashSet<>();

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) throw new Exception404("장바구니에 담은 상품이 없습니다.");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        requestDTOs.forEach(c -> set.add(c.getCartId()));
        if (requestDTOs.size() != set.size()) throw new Exception400("동일 상품이 동시에 요청 들어왔습니다.");

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for (Integer cartId  : set) {
            boolean exist = cartJPARepository.existsById(cartId);
            if (!exist) throw new Exception404("해당 장바구니를 찾을 수 없습니다.");
        }

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
