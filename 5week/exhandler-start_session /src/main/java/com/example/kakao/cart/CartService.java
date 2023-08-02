package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTOv2(cartList);
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }



    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        List<Integer> optionIds = new ArrayList<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            if (optionIds.contains(optionId)) {
            throw new Exception400("동일한 옵션을 여러번 들어왔습니다.: " + optionId);
        } else{
                optionIds.add(optionId);
                System.out.println(optionIds);}
        }
        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        List<Cart> carts = cartJPARepository.findAllByUserId(sessionUser.getId());
        Set<Integer> cartRepositorySet = carts.stream()
                .map(cart -> cart.getOption().getId())
                .collect(Collectors.toSet());
        // 옵션이 있는지 확인
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            //2. 존재하면 장바구니에 수량을 추가하는 업데이트 진행
            if (cartRepositorySet.contains(optionId)){
                updateCartQuantity(carts, optionId, quantity, optionPS.getPrice());
            }
            else {
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    } // 변경감지, 더티체킹, flush, 트랜젝션 종료

    private void updateCartQuantity(List<Cart> carts, int optionId, int quantity, int price) {
        for (Cart cart : carts) {
            if (cart.getOption().getId() == optionId) {
                int newQuantity = cart.getQuantity() + quantity;
                int newPrice = newQuantity * price;
                cart.update(newQuantity, newPrice);
            }
        }
    }


    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (carts.isEmpty()){
            throw new Exception400("장바구니가 비었습니다");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<Integer> cartIds = new ArrayList<>();
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            if (cartIds.contains(cartId)) {
                throw new Exception400("동일한 장바구니 아이디가 여러번 들어왔습니다.: " + cartId);
            } else{
                cartIds.add(cartId);
                System.out.println(cartIds);}
        }
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : carts) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(carts);
    } // 더티체킹
}