package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
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

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final EntityManager em;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        System.out.println(sessionUser.getUsername());
        boolean isDuplicated = requestDTOs
                .stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .distinct()
                .count() != requestDTOs.size();
        if (isDuplicated){
            throw new Exception400("동일한 장바구니를 추가할 수는 없습니다.");
        }
        // 2. quantity가 0이 된 경우 예외처리
        boolean isQuantityWrong = requestDTOs.stream().anyMatch(
                requestCart -> requestCart.getQuantity() <= 0
        );
        if(isQuantityWrong){
            throw new Exception400("장바구니 수량은 1개 이상이어야 합니다.");
        }
        // 3. cartJPARepository.findByOptionIdAndUserId() 조회
        for (CartRequest.SaveDTO requestDTO: requestDTOs){
            Optional<Cart> cart = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());
            if (cart.isPresent()){
                // 4-1. 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
                Cart mcart = cart.get();
                mcart.update(requestDTO.getQuantity(), mcart.getOption().getPrice() * requestDTO.getQuantity());
            } else {
                // 4-2. [2번이 아니라면] 유저의 장바구니에 담기
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                Cart mcart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(mcart);
            }
        }
        em.flush();
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
        if (cartList.isEmpty()){
            throw new Exception400("유저 장바구니에 상품이 존재하지 않습니다.");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        boolean isDuplicated = requestDTOs
                .stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .distinct()
                .count() != requestDTOs.size();
        if (isDuplicated){
            throw new Exception400("동일한 장바구니를 업데이트할 수는 없습니다.");
        }

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        boolean isExist = requestDTOs.stream().allMatch(
                requestCart -> cartList.stream().anyMatch(cart -> cart.getId() == requestCart.getCartId())
        );
        if (!isExist){
            throw new Exception400("존재하지 않는 cartId입니다.");
        }

        // 4. quantity가 0이 된 경우 예외처리
        boolean isQuantityWrong = requestDTOs.stream().anyMatch(
                requestCart -> requestCart.getQuantity() <= 0
        );
        if(isQuantityWrong){
            throw new Exception400("장바구니 수량은 1개 이상이어야 합니다.");
        }

        // 장바구니 업데이트
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
