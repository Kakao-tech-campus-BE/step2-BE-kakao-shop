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

@Service
@RequiredArgsConstructor
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
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ] 와 같이 요청이 들어오면?
        // optionId:1, quantity:15로 처리되는 것이 맞
        Map<Integer, Integer> optionIdToQuantityMap = new HashMap<>(); // Keep track of optionId and total quantity

        for (CartRequest.SaveDTO saveDTO : requestDTOs) {
            int optionId = saveDTO.getOptionId();
            int quantity = saveDTO.getQuantity();

            if (optionIdToQuantityMap.containsKey(optionId)) {
                int currentQuantity = optionIdToQuantityMap.get(optionId);
                optionIdToQuantityMap.put(optionId, currentQuantity + quantity);
            } else {
                optionIdToQuantityMap.put(optionId, quantity);
            }
        }

        Set<Integer> optionIdSet = optionIdToQuantityMap.keySet();

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        for (int i = 0; i < optionIdToQuantityMap.size(); i++) {
            CartRequest.SaveDTO saveDTO = requestDTOs.get(i);
            Cart cart = cartJPARepository.findByOptionIdAndUserId(saveDTO.getOptionId(), sessionUser.getId()).get();

            // 장바구니 존재할 때 -> 업데이트
            if (!cart.equals(null)) {
                cart.update(cart.getQuantity() + saveDTO.getQuantity(),
                        cart.getPrice() + saveDTO.getQuantity() * cart.getOption().getPrice());
            } else {

                // 장바구니 존재하지 않을 때
                int optionId = saveDTO.getOptionId();
                int quantity = saveDTO.getQuantity();
                Option optionPS = optionJPARepository.findById(saveDTO.getOptionId())
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart newCart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
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
        cartList.forEach(c -> set.add(c.getId()));
        if (cartList.size() != set.size()) throw new Exception400("동일 상품이 동시에 요청 들어왔습니다.");

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for (int i = 0; i < requestDTOs.size(); i++) {
            if (!cartList.contains(requestDTOs.get(i))) throw new Exception404("해당 장바구니를 찾을 수 없습니다.");
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

