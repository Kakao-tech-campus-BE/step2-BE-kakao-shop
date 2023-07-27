package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User user) {
        for (CartRequest.SaveDTO saveDTO : requestDTOs) {
            Option option = optionJPARepository.findById(saveDTO.getOptionId()).orElseThrow(
                    () -> new Exception400("상품 옵션을 찾을 수 없습니다. : "));
            try {
                cartJPARepository.save(new Cart(user, option, saveDTO.getQuantity(), option.getPrice()*saveDTO.getQuantity()));
            } catch (Exception e) {
                throw new Exception500("unknown server error");
            }
        }
    }

    @Transactional
    public CartResponse.FindAllDTO findAll(User user) {
        try {
            return new CartResponse.FindAllDTO(cartJPARepository.findAllByUser(user));
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }

    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs) {

        List<Cart> carts = new ArrayList<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            Cart cart = cartJPARepository.findById(updateDTO.getCartId()).orElseThrow(
                    () -> new Exception400("상품 옵션을 찾을 수 없습니다. : "));;
            cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
            carts.add(cart);
        }
        // DTO를 만들어서 응답한다.
        CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(carts);
        return responseDTO;
    }

    @Transactional
    public void clear(User user) {
        try {
            cartJPARepository.deleteAllByUser(user);
        } catch (Exception e) {
            throw new Exception500("unknown server error");
        }
    }
}
