package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;

    public CartService(CartJPARepository cartJPARepository, OptionJPARepository optionJPARepository, UserJPARepository userJPARepository) {
        this.cartJPARepository = cartJPARepository;
        this.optionJPARepository = optionJPARepository;
        this.userJPARepository = userJPARepository;
    }


    public void addCartList(List<CartRequest.SaveDTO> saveDTOs, int userId) {
        for(CartRequest.SaveDTO s:saveDTOs){
            Optional<Cart> opCart = cartJPARepository.findByOption_IdAndUserId(s.getOptionId(),userId);
            if (opCart.isPresent()){
                Cart cart = opCart.get();
                cart.update(cart.getQuantity()+s.getQuantity(), cart.getPrice()/cart.getQuantity()*(cart.getQuantity()+s.getQuantity()));
            }
            else{
                Option option = optionJPARepository.findById(s.getOptionId()).orElseThrow(()->new Exception400("해당 옵션 번호는 없는 번호입니다."));
                User user =  userJPARepository.findById(userId).orElseThrow(()->new Exception400("현재 접속중인 유저는 없는 유저입니다."));
                Cart cart = Cart.builder().option(option).price(option.getPrice()*s.getQuantity()).user(user).quantity(s.getQuantity()).build();
                cartJPARepository.save(cart);
            }
        }

        //todo 옵션이 이미 있다면 수량추가
    }

    public CartResponse.FindAllDTO findAll(int userId) {
        List<Cart> carts = cartJPARepository.findAllByUserId(userId);
        return new CartResponse.FindAllDTO(carts);
    }

    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> updateDTOs, int userId) {
        for(CartRequest.UpdateDTO u:updateDTOs){
            Cart cart = cartJPARepository.findById(u.getCartId()).orElseThrow(()->new Exception400("해당 카트는 없는 카드 입니다."));
            if (cart.getUser().getId()!=userId){
                throw new Exception400("접속한 유저의 카트가 아닙니다.");
            }
            cart.update(u.getQuantity(),cart.getPrice()/ cart.getQuantity()*u.getQuantity()); //옵션을 안가져온 이유는 쿼리를 최소한으로 하기 위함
        }


        return null;
    }

    public void clear(int userId) {
        cartJPARepository.deleteAllByUserId(userId);
    }
}
