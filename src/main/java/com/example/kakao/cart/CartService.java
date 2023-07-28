package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.web.firewall.RequestRejectedException;
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
        if(cartList.isEmpty()){
            throw new Exception400("장바구니에 제품이 들어있지 않습니다!");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<Integer> CartId = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toList());
        long checkCount = CartId.stream().distinct().count();
        if (checkCount != CartId.size())
            throw new Exception400("동일한 장바구니 존재!");

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        List<Integer> requestCartId = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCartId)
                .collect(Collectors.toList());
            if (requestDTOs.size() != requestDTOs.stream().mapToInt(CartRequest.UpdateDTO::getCartId).distinct().count()) {
                throw new Exception400("존재하지 않는 장바구니입니다.:");
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

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser, Cart cart ) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(sessionUser.getId());
            // 1. 동일한 옵션이 들어오면 예외처리
            // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
            for (CartRequest.SaveDTO saveDTO : requestDTOs) {
                int optionId = saveDTO.getOptionId();

                for(Cart existingCart : cartList){
                    if (existingCart.getId() == optionId) {
                        throw new Exception400("동일한 옵션의 상품이 이미 장바구니에 있습니다." + optionId);

                    }
                }
            }

            //조회 userId:1, optionId:1
            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            //[cartId:1, optionId:1, quantity:3, userId:1] -> DTO { optionId: 1, quantity:5}
            int cartId = cart.getId();
            for(CartRequest.SaveDTO requestDTO : requestDTOs) {
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
                Cart cartPS = cartJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 장바구니를 찾을 수 없습니다."));
                int newQuantity = cart.getQuantity() + cart.getQuantity();
                int newPrice = cart.getPrice()* quantity;
                Cart newcart = Cart.builder().user(sessionUser).quantity(newQuantity).price(newPrice).build();
                cartJPARepository.save(cart);
            }



            // 3. [2번이 아니라면] 유저의 장바구니에 담기
            for (CartRequest.SaveDTO requestDTO : requestDTOs) {
                int optionId = requestDTO.getOptionId();
                int quantity = requestDTO.getQuantity();
                Option optionPS = optionJPARepository.findById(optionId)
                        .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity;
                cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        } //변경감지, 더티체킹, flush, 트랜잭션


}
