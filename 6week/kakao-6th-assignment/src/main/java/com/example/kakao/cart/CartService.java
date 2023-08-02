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

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());

        if (cartList.isEmpty()){
            throw new Exception404("장바구니가 비어있습니다 : "+cartList);
        }

        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        // 1. 동일한 옵션이 들어오면 예외처리하기 위한 Set 선언
        Set<Integer> optionIdSet = new HashSet<>();

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            if (quantity <= 0 || quantity >= 1000){
                throw new Exception400("잘못된 수량 요청입니다. : "+quantity);
            }

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = requestDTO.getPrice();

            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5, price:50000 }, { optionId:1, quantity:10, price:10000 } ]
            if (optionIdSet.contains(optionId)){
                throw new Exception400("중복된 옵션 요청입니다. : " + optionId);
            }
            optionIdSet.add(optionId);

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            Optional<Cart> existingCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            if (existingCart.isPresent()) {
                // 장바구니에 해당 옵션이 이미 존재하는 경우, 수량을 더하여 업데이트
                Cart cartToUpdate = existingCart.get();
                int newQuantity = cartToUpdate.getQuantity() + quantity;
                int newPrice = cartToUpdate.getPrice() + price;
                int newPriceCheck = optionPS.getPrice() * newQuantity;
                cartToUpdate.setQuantity(newQuantity);
                cartToUpdate.setPrice(newPriceCheck);
                cartJPARepository.save(cartToUpdate);
                /*if (newPrice == newPriceCheck) {
                    cartToUpdate.setQuantity(newQuantity);
                    cartToUpdate.setPrice(newPrice);
                    cartJPARepository.save(cartToUpdate);
                } else {
                    throw new Exception400("잘못된 가격 요청입니다. : " + price);
                }*/
            } else {
                // 3. 장바구니에 해당 옵션이 존재하지 않는 경우, 새로운 장바구니 정보 생성
                int priceCheck = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(priceCheck).build();
                cartJPARepository.save(cart);
                /*if (price == priceCheck) {
                    Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                    cartJPARepository.save(cart);
                } else {
                    throw new Exception400("잘못된 가격 요청입니다. " + price);
                }*/
            }
        }
    } // 여기서 변경 감지, 더티체킹, flush, 트랜잭션 종료

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()){
            throw new Exception400("장바구니에 상품이 존재하지 않습니다. : " + cartList);
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        Set<Integer> cartIdSet = new HashSet<>();

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        boolean foundInCartList;

        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();

            if (cartIdSet.contains(cartId)) {
                throw new Exception400("중복된 장바구니 아이디 요청입니다. : " + cartId);
            }
            cartIdSet.add(cartId);

            foundInCartList = false;
            for (Cart cart : cartList){
                if(cart.getId() == cartId) {
                    foundInCartList = true;
                    break;
                }
            }
            if (!foundInCartList) {
                throw new Exception400("없는 장바구니 아이디 요청입니다. : " + cartId);
            }
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {

                    int cartQuantity = updateDTO.getQuantity();
                    if (cartQuantity <= 0 || cartQuantity >= 1000){
                        throw new Exception400("잘못된 수량 요청입니다. : "+cartQuantity);
                    }

                    int cartPrice = updateDTO.getPrice();
                    int cartPriceCheck = cart.getOption().getPrice() * cartQuantity;

                    cart.update(cartQuantity, cartPriceCheck);
                    /*
                    if (cartPrice == cartPriceCheck){
                        cart.update(cartQuantity, cartPrice);
                    }else{
                        throw new Exception400("잘못된 가격 요청 입니다. : " + cartPrice);
                    }
                    */
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
