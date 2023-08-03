package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final EntityManager em;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        List<Integer> optionIdList = requestDTOs.stream().map(r -> r.getOptionId()).collect(Collectors.toList());
        for (int optionId : optionIdList) {
            if(Collections.frequency(optionIdList, optionId) > 1) {
                throw new Exception400("동일한 옵션이 중복되어 들어왔습니다 : " + optionId);
            }
        }

        int userId = sessionUser.getId();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            // 4. 만약 요청 개수가 0개라면 장바구니에 추가하지 않는다.
            if (requestDTO.getQuantity() == 0) {
                continue;
            }
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            Cart prevCart = cartJPARepository.findByOptionIdAndUserId(optionId, userId).orElse(null);
            if (prevCart != null) {
                quantity += prevCart.getQuantity();
                price += prevCart.getPrice();
                prevCart.update(quantity, price);
            }
            // 3. [2번이 아니라면] 유저의 장바구니에 담기
            else {
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
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
        if (cartList.isEmpty()) {
            throw new Exception404("주문할 장바구니 상품이 없습니다");
        }

        List<Integer> updateListId = requestDTOs.stream().map(r -> r.getCartId()).collect(Collectors.toList());
        List<Integer> cartListId = cartList.stream().map(c -> c.getId()).collect(Collectors.toList());
        for (int updateId : updateListId) {
            // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
            if(Collections.frequency(updateListId, updateId) > 1) {
                throw new Exception400("동일한 장바구니 아이디를 주문할 수 없습니다 : " + updateId);
            }
            // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
            if (!cartListId.contains(updateId)) {
                throw new Exception400("장바구니에 없는 상품은 주문할 수 없습니다 : " + updateId);
            }
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : cartList){
                if (cart.getId() == updateDTO.getCartId()) {
                    // 4. 만약 개수가 0이라면 해당 장바구니를 삭제한다.
                    if (updateDTO.getQuantity() == 0) {
                        cartJPARepository.deleteById(updateDTO.getCartId());
                        cartList.remove(cart);
                    } else {
                        cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                    }
                    break;
                }
            }
        }

        // 5. update 후 주문할 상품이 0개이면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("0개의 상품을 주문할 수 없습니다");
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
