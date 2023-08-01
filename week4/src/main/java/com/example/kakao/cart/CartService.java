package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao._core.security.JWTProvider;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import com.example.kakao.user.UserRequest;
import com.example.kakao.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    //장바구니 조회
    public CartResponse.FindAllDTO findAll(){
        List<Cart> cartList = cartJPARepository.findAll();
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
        return responseDTO;
    }

    //장바구니 담기
    @Transactional
    public void save(List<CartRequest.SaveDTO> dtoList, CustomUserDetails userDetails){
        dtoList.forEach(
                saveDTO -> System.out.println("요청 받은 장바구니 옵션 : "+saveDTO.toString())
        );
        try {
            Option option = null;
            Cart cart = null;
            for (CartRequest.SaveDTO saveDTO : dtoList) {
                Optional<Option> optional = optionJPARepository.findById(saveDTO.getOptionId());
                System.out.println("optional"+optional);
                if (optional.isEmpty()) throw new Exception404("해당 option이 없습니다.");
                option = optional.get();
                cart = Cart.builder()
                        .user(userDetails.getUser())
                        .option(option)
                        .quantity(saveDTO.getQuantity())
                        .price(option.getPrice() * saveDTO.getQuantity())
                        .build();
            }
                cartJPARepository.save(cart);
        }
        catch (Exception404 e){
            throw e;
        }
        catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }
    //장바구니 수정
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs){
        requestDTOs.forEach(
                updateDTO -> System.out.println("요청 받은 장바구니 수정 내역 : "+updateDTO.toString())
        );
        try {
            List<Cart> cartList = cartJPARepository.findAll();
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                for (Cart cart : cartList) {
                    if(cart.getId() == updateDTO.getCartId()){
                        cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                    }
                }
            }
            cartList = cartJPARepository.findAll();
            // DTO를 만들어서 응답한다.
            CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(cartList);
            System.out.println("responseDTO" + responseDTO);
            return responseDTO;
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

}
