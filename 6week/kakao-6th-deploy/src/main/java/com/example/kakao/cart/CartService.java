package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    // 5주차 과제 : 장바구니 담기 -> 예외 처리하기
    /*
        흠 Cart에서 Price를 칼럼으로 들고 있으면 만약 해당 Option의 가격이 할인행사 같은거로
        가격 변동이 생긴다면??? 모든 Cart에 Price 값을 다시 계산해줘야하는 문제가 생길거 같긴함
        => 일단 고려 x

     */
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]

        Set<Integer> optionIds = new HashSet();

        for (CartRequest.SaveDTO requestDTO : requestDTOs){
            int optionId = requestDTO.getOptionId();
            if (!optionIds.contains(optionId)){
                optionIds.add(optionId);
            }else{
                throw new Exception400("동일한 옵션이 중복되어 들어왔습니다: " + optionId);
            }
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Optional<Cart> byOptionIdAndUserId = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());

            if(byOptionIdAndUserId.isPresent()){ // 존재하면 장바구니에 수량만 업데이트
//                Option optionPS = optionJPARepository.findById(optionId).orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
//                int price = optionPS.getPrice() * quantity;
                byOptionIdAndUserId.get().plusQuantity(quantity);

            }else{
                Option optionPS = optionJPARepository.findById(optionId).orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
                int price = optionPS.getPrice() * quantity; // 가격 받아오기위해 option findById
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build(); // 이건 새로 만드는거 !
                cartJPARepository.save(cart);
            }

        }
    }

    // 5주차 과제 : 장바구니 수정(주문하기) -> 예외처리하기
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // List<Cart> => List<Integer>
        List<Integer> cartListIds = cartList.stream().map((o1)->{
            return o1.getId();
        }).collect(Collectors.toList());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if(cartListIds.size() == 0 ){
            throw new Exception404("주문할 장바구니 상품이 없습니다");
        }


        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        Set<Integer> cartIds = new HashSet();

        for (CartRequest.UpdateDTO requestDTO : requestDTOs){
            int cartId = requestDTO.getCartId(); // 이게 cartList에 존재하는지 체크?

            if (!cartIds.contains(cartId)){
                cartIds.add(cartId);
            }else{
                throw new Exception400("동일한 장바구니 아이디를 주문할 수 없습니다" + cartId);
            }

            if(!cartListIds.contains(cartId)){
                throw new Exception400("장바구니에 없는 상품은 주문할 수 없습니다 : " + cartId);
            }

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


    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

}
