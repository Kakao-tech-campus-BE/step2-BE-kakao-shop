package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    CartJPARepository cartJPARepository;
    UserJPARepository userJPARepository;
    OptionJPARepository optionJPARepository;
    EntityManager em;

    public int addCartList(List<CartRequest.SaveDTO> requestDTOs){
        User user = userJPARepository.findById(1).get();
        Cart cart;
        Option option;
        //옵션의 ID를 받았을 때 존재하지 않는 옵션이면 오류
        for (int i=0;i< requestDTOs.size();i++) {
            option = optionJPARepository.findById(requestDTOs.get(i).getOptionId()).orElseThrow(
                    () -> new Exception400("존재하지 않는 옵션이 포함되어 있습니다."));

            //옵션이 실제 존재하는 옵션일 경우, 유저의 Cart에 저장
            cart = Cart.builder()
                    .price(option.getPrice())
                    .quantity(requestDTOs.get(i).getQuantity())
                    .option(option)
                    .user(user).build();
            cartJPARepository.save(cart);
        }
        em.flush();
        //성공했으면 리턴하지 않음
        return 0;
    }
    //모든 카트를 찾는 서비스
    public CartResponse.FindAllDTO findAll(){
        List<Cart> cartList = cartJPARepository.findAll();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        return responseDTO;
    }
    //카트의 내용을 업데이트하고 cart의 내용물을 반환함. 유저의 id는 1

    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs){
        User user = userJPARepository.findById(1).get();
        for (int i=0;i<requestDTOs.size();i++) {
            Optional<Cart> cartOP = cartJPARepository.findById(requestDTOs.get(i).getCartId());
            Cart cart;
            //카트의 id를 통해 카트를 찾는데 카트가 그 유저에게 존재하지 않을 경우에 오류 throw하고 그 유저에게 존재할 경우 update로직을 실행한다.
            if (cartOP.isPresent()) {
                cart = cartOP.get();
                cart.update(cart.getQuantity(), cart.getOption().getPrice()*cart.getQuantity());
            }
            else{
                throw new Exception400("찾을 수 없는 장바구니 내역이 포함되어 있습니다.");
            }
        }
        //flush로 저장
        em.flush();
        List<Cart> cartList = cartJPARepository.findByUserId(user.getId());
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(cartList);
        return responseDTO;
    }
    //카트의 내용을 초기화하는 서비스
    public int clear(){
        cartJPARepository.deleteAll();
        em.flush();
        return 0;
    }
}
