package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
    	
    	// 트랜잭션은 최대한 짧게 가져가는 것이 좋음 -> 따라서 데이터베이스에 접근하기 이전 앞서서 걸러낼 수 있는 것들은 예외처리
    	// 2번을 위해 for문을 따로 돌려보는 것보다는 HashSet을 이용하여 한번 for문이 돌 때 한번에 체킹하는 편이 좋다고 생각했음
    	Set<Integer> uniqueOptionIds = new HashSet<>();
    	
    	
    	for (CartRequest.SaveDTO requestDTO : requestDTOs) {

            Integer optionId = requestDTO.getOptionId();
            Integer quantity = requestDTO.getQuantity();

            // 1. null값이 들어왔을 때 예외처리
            if (optionId == null || quantity == null) {
                throw new Exception400("잘못된 요청입니다 : 인덱스:" + optionId + " 수량:" + quantity);
            }
            
            // 2. 애초에 요청할 때 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            if (!uniqueOptionIds.add(optionId)) {
                throw new Exception400("중복된 요청입니다 : " + optionId);
            }
            
            
            // 3. 유저fk와 옵션fk가 일치하는 기존에 존재하는 장바구니에 대해서는 수량을 추가하는 방식으로 함.
            Optional<Cart> existingCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            
            if (existingCart.isPresent()) {
                Cart cart = existingCart.get();
                cart.setQuantity(cart.getQuantity() + quantity);
                cartJPARepository.save(cart);
            }
            
            // 4. 기존에 존재하지 않을 경우 새로운 cart생성
            else {
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }

    	}
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개일 때, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        if (cartList.isEmpty()) {
        	throw new Exception404("장바구니는 비어있습니다 : " + user.getId());
        }
        
        return new CartResponse.FindAllDTO(cartList);
    }

/* 전체 상품조회 버전 2 - 프론트입장에서 좋은 코드가 아니어서 사용하지 않음.
    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }
*/

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
    	
    	// 0. 유저fk에 따른 장바구니 리스트 가져오기
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
        	throw new Exception404("결제할 상품이 존재하지 않습니다 :" + user.getId());
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        // Map을 사용하면 3번에서 코드 재사용을 할 수 있을 것같아 사용함.
        Map<Integer, CartRequest.UpdateDTO> cartRequestMap = new HashMap<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            if (cartRequestMap.containsKey(requestDTO.getCartId())) {
                throw new Exception400("중복된 결제 요청입니다 :" + requestDTO.getCartId());
            }
            cartRequestMap.put(requestDTO.getCartId(), requestDTO);
        }

        // 3. 장바구니 항목을 업데이트하고 잘못된 요청을 확인
        for (Cart cart : cartList) {
            CartRequest.UpdateDTO updateDTO = cartRequestMap.get(cart.getId());
            if (updateDTO == null) {
                throw new Exception400("부적절한 결제요청입니다 :" + cart.getId());
            }
            cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
