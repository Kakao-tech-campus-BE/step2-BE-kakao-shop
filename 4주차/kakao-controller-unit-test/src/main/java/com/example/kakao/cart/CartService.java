package com.example.kakao.cart;


import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.UserJPARepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;


    public List<Cart> findAllCart(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<Cart> carts = cartJPARepository.findByUserId(userDetails.getUser().getId());
        if(carts.isEmpty()){
            throw new Exception400("빈 장바구니입니다");
        }
        return carts;
    }


    public void addCart(List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<Cart> carts = new ArrayList<>();

        //현재 유저의 카트 정보 가져오기
        List<Cart> alreayCartList = cartJPARepository.findByUserId(userDetails.getUser().getId());

        //reuqestDTOs를 각각 carts 리스트에 담아준다.
        for (CartRequest.SaveDTO a : requestDTOs) {
            for (Cart b : alreayCartList) {
                if (b.getOption().getId() == a.getOptionId())
                    throw new Exception400("이미 장바구니에 존재합니다");
            } //사용자의 장바구니에 이미 해당 옵션이 존재하는경우 에러
            Cart cart = new Cart();
            carts.add(cart.newCart(
                    userDetails.getUser(),
                    optionJPARepository.getReferenceById(a.getOptionId()),
                    a.getQuantity()));
        }
        cartJPARepository.saveAll(carts);
    }

    @Transactional
    public List<Cart> updateCart(List<CartRequest.UpdateDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("-------------------------");
        List<Cart> cartList = new ArrayList<>();
        //현재 유저의 cart 리스트를 가져옴
        try {
            for (CartRequest.UpdateDTO a : requestDTOs) {
                //현재 유저의 id와 cartId가 일치한 카트를 가져옴
                Cart cart = cartJPARepository.findById(a.getCartId()).orElseThrow(
                        () -> new Exception400("존재하지 않는 장바구니 입니다.")
                );
                if (cart.getUser().getId() != userDetails.getUser().getId()) {
                    throw new Exception400("잘못된 접근입니다");
                }
                cart.update(a.getQuantity(), cart.getPrice() * a.getQuantity());
                //해당 카트의 가격을 변경된 개수*가격으로 변경.
                cartList.add(cart);

            }
            return cartList;
        }catch(RuntimeException e){
            throw new Exception400("서버에러");
        }

    }
}
