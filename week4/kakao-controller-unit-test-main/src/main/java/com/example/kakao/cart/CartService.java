package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.security.CustomUserDetails;
import com.example.kakao.log.ErrorLog;
import com.example.kakao.log.ErrorLogJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ErrorLogJPARepository errorLogJPARepository;

    @Transactional
    public void add(List<CartRequest.SaveDTO> requestDTOs, @AuthenticationPrincipal CustomUserDetails userDetails){
        try{
            for (CartRequest.SaveDTO requestDTO : requestDTOs) {
                Option option = optionJPARepository.findById(requestDTO.getOptionId()).orElseThrow(
                        () -> new Exception400("옵션을 찾을 수 없습니다. : " + requestDTO.getOptionId())
                );
                Cart cart = Cart.builder()
                        .user(userDetails.getUser())
                        .option(option)
                        .quantity(requestDTO.getQuantity())
                        .price(option.getPrice() * requestDTO.getQuantity())
                        .build();
                cartJPARepository.save(cart);
            }
        }catch (Exception400 e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw e;
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }

    public CartResponse.FindAllDTO findAll(){
        try{
            //List<Cart> findAllList = cartJPARepository.mFindByUserId(id);
            List<Cart> findAllList = cartJPARepository.findAll();
            return new CartResponse.FindAllDTO(findAllList);
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs){
        try{
            List<Cart> carts = new ArrayList<>();
            for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
                Cart cart = cartJPARepository.mFindById(requestDTO.getCartId()).orElseThrow(
                        () -> new Exception400("카트를 찾을 수 없습니다. : " + requestDTO.getCartId())
                );

                cart.update(requestDTO.getQuantity(), requestDTO.getQuantity() * cart.getOption().getPrice());
                cartJPARepository.update(cart);

                Cart findCart = cartJPARepository.mFindById(cart.getId()).orElseThrow(
                        () -> new Exception400("카트를 찾을 수 없습니다. : " + cart.getId())
                );

                carts.add(findCart);
            }
            return new CartResponse.UpdateDTO(carts);
        }catch (Exception400 e){
            System.out.println("cart update 400 error");
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw e;
        }catch (Exception e){
            ErrorLog errorLog = ErrorLog.builder()
                    .message(e.getMessage())
                    .build();
            errorLogJPARepository.save(errorLog);
            throw new Exception500("unknown server error");
        }
    }

//    @Transactional
//    public void clear(){
//        try{
//            cartJPARepository.deleteAll();
//        }catch (Exception e){
//            ErrorLog errorLog = ErrorLog.builder()
//                    .message(e.getMessage())
//                    .build();
//            errorLogJPARepository.save(errorLog);
//            throw new Exception500("unknown server error");
//        }
//    }
}
