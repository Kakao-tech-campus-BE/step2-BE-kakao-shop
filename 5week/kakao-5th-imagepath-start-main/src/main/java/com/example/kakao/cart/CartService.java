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

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) throws Exception404, Exception400 {
        // Check if sessionUser exists in the database
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        List<Integer> optionIdList = requestDTOs.stream().map(CartRequest.SaveDTO::getOptionId).collect(Collectors.toList());
        for(int optionId : optionIdList){
            if(optionIdList.indexOf(optionId) != optionIdList.lastIndexOf(optionId))
                throw new Exception400("중복된 옵션 존재, optioinID :" + optionId);
        }
        // 수량이 1보다 적은 경우 예외처리
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int quantity = requestDTO.getQuantity();
            if (quantity < 1) {
                throw new Exception400("수량이 1보다 적습니다: " + quantity);
            }
        }
        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 상품 옵션 조회 및 없는 경우 예외 처리
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;

            // 해당 옵션과 사용자로 장바구니 항목 조회
            Cart existingCart = cartJPARepository.findByOptionIdAndUserId(optionId, user.getId())
                    .orElse(null);

            if (existingCart != null) {
                // 장바구니에 해당 상품이 이미 있다면, 수량과 가격 업데이트
                int newPrice = optionPS.getPrice() * quantity;
                existingCart.update(quantity, newPrice);
            } else {
                // 장바구니에 해당 상품이 없다면, 새 항목 추가
                Cart newCart = Cart.builder()
                        .user(user)
                        .option(optionPS)
                        .quantity(quantity)
                        .price(price)
                        .build();
                cartJPARepository.save(newCart);
            }
        }
    }
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findCartByUserId(user.getId());
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findCartByUserId(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다.");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<Integer> cartIdList = requestDTOs.stream().map(CartRequest.UpdateDTO::getCartId).collect(Collectors.toList());
        for(int cartId : cartIdList){
            if(cartIdList.indexOf(cartId) != cartIdList.lastIndexOf(cartId))
                throw new Exception400("중복된 장바구니 존재, cartId :" + cartId);
        }
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            Cart existingCart = cartJPARepository.findById(cartId)
                    .orElseThrow(() -> new Exception404("해당 장바구니를 찾을 수 없습니다. cartId : " + cartId));
            if (existingCart.getUser().getId() != user.getId()) {
                throw new Exception400("해당 장바구니는 유저의 장바구니가 아닙니다 : " + cartId);
            }
        }

        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    int quantity = updateDTO.getQuantity();
                    if (quantity < 1) {
                        throw new Exception400("수량이 1보다 적습니다: " + quantity);
                    }
                    int price = cart.getOption().getPrice() * quantity;

                    // 카트 엔티티 업데이트
                    cart.update(quantity, price);
                }
            }
        }


        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
