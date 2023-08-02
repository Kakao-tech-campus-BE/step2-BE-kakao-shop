package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        boolean sameOptionRequest = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .distinct()
                .count() != requestDTOs.size();

        if(sameOptionRequest){
            throw new Exception404("동일한 옵션이 들어옴");
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());

            // case 1 - 이미 존재한 cart는 갯수 업데이트
            if (cartOP.isPresent()) {
                Cart cart = cartOP.get();
                cart.update(cart.getQuantity() + requestDTO.getQuantity(), cart.getPrice());
            } else { // case 2 - 장바구니에 없던 cart
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
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
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Prsoduct는 2개인 것이다.
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
        if(cartList.isEmpty()){
            throw new Exception404("장바구니가 텅 비어있음");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        boolean hasSameCartId = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .distinct()
                .count() != requestDTOs.size();

        if(hasSameCartId){
            throw new Exception404("동일한 장바구니 아이디가 들어옴");
        }

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리

        /* 스트림을 사용하지 않은 방법 -> 길고 비효율적이라 stream을 사용하자
        boolean noCartIdInCart = true;
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            for (Cart cart : cartList) {
                if(updateDTO.getCartId() == cart.getId()){
                    noCartIdInCart = false;
                    break;
                }
            }
        }
        */
        boolean noCartIdInCart = requestDTOs.stream()
                .anyMatch(updateDTO -> cartList.stream().noneMatch(cart -> updateDTO.getCartId() == cart.getId()));

        if (noCartIdInCart) {
            throw new Exception404("장바구니에 없는 cartId가 들어옴");
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
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
