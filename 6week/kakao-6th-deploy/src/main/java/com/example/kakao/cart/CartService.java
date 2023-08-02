package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    private final UserJPARepository userJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> saveDTOs, int userId) {
        List<Integer> requestIds = new ArrayList<>();
        for(CartRequest.SaveDTO s:saveDTOs){
            if (requestIds.contains(s.getOptionId())){
                throw new Exception400("요청에 동일한 옵션이 두개이상 포함되어있습니다.");
            }
            requestIds.add(s.getOptionId());
            Optional<Cart> opCart = cartJPARepository.findByOptionIdAndUserId(s.getOptionId(),userId);
            if (opCart.isPresent()){ //이미 있는 옵션이라면 갯수추가
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
    }

    public CartResponse.FindAllDTO findAll(int userId) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(userId);
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(int userId) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(userId);
        return new CartResponse.FindAllDTOv2(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, int userId) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(userId);
        // 1. 유저 장바구니에 아무것도 없으면 예외처리 o
        if (cartList.isEmpty()){
            throw new Exception400("해당 유저의 장바구니가 비어있습니다.");
        }
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리 o
        List<Integer> requestIds = new ArrayList<>();
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리 o
        List<Integer> cartIds = cartList.stream().map(c-> c.getId()).collect(Collectors.toList());
        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            if (requestIds.contains(updateDTO.getCartId())){
                throw new Exception400("요청에 동일한 장바구니가 두개이상 포함되어있습니다.");
            }
            requestIds.add(updateDTO.getCartId());
            if (!cartIds.contains(updateDTO.getCartId())){
                throw new Exception400("요청하신 장바구니는 해당 유저의 장바구니가 아닙니다.");
            }
            for (Cart cart : cartList) {
                if (cart.getId() == updateDTO.getCartId()) {
                    if (updateDTO.getQuantity()<1){
                        throw new Exception400("옵션의 개수가 1보다 작을 수 없습니다.");
                    }
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }

            }
        }

        // 아래처럼 구현하면 쿼리가 더 많이 나가서 비효율적?? 서버가 비효율적인것 보다, 쿼리가 적게 나가는 것이 더 좋나?
//        List<Integer> requestIds = new ArrayList<>();
//        for(CartRequest.UpdateDTO u:requestDTOs){
//            if (requestIds.contains(u.getCartId())){
//                throw new Exception400("요청에 동일한 장바구니가 두개이상 포함되어있습니다.");
//            }
//            requestIds.add(u.getCartId());
//            Cart cart = cartJPARepository.findById(u.getCartId()).orElseThrow(()->new Exception400("해당 카트는 없는 카드 입니다."));
//            if (cart.getUser().getId()!=userId){
//                throw new Exception400("접속한 유저의 카트가 아닙니다.");
//            }
//            cart.update(u.getQuantity(),cart.getPrice()/ cart.getQuantity()*u.getQuantity()); //옵션을 안가져온 이유는 쿼리를 최소한으로 하기 위함
//        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹

    @Transactional
    public void clear(int userId) {
        cartJPARepository.deleteAllByUserId(userId);
    }
}

