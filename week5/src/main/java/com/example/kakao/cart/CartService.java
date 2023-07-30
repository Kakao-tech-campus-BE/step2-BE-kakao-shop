package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

    @Transactional // Transactional을 다시 해주지 않으면 update 쿼리가 작동하지 않음
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // (원래 상태에서도 트랜젝션을 타서 잡아내긴 하지만 최적화 필요)
        Long totalCount = requestDTOs.stream().map(CartRequest.SaveDTO::getOptionId).count();
        Long uniqueCount = requestDTOs.stream().map(CartRequest.SaveDTO::getOptionId).distinct().count();
        if (uniqueCount < totalCount) throw new Exception400("중복 옵션 요청이 존재합니다 : ");

        // 만약 requestDTO로 price를 받게 된다면 다음과 같이 코드를 짤 수 있지만
        // 신뢰할 수 없기 때문에 어차피 검증 절차가 추가되어야 할 것이다
//        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
//            int optionId = requestDTO.getOptionId();
//            int quantity = requestDTO.getQuantity();
//            int price = requestDTO.getPrice();
//            cartJPARepository.mSave(sessionUser.getId(), optionId, quantity, price);
//        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회
        // 이미 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함 (더티체킹)
        // 원래 상태에서는 uniqueConstraint 제약때문에 에러가 발생
        // 3. 존재하지 않는다면 유저의 장바구니에 담기

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;

            Optional<Cart> existence = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());

            // 장바구니에 해당 옵션이 이미 존재하는 경우
            if (existence.isPresent()) {
                int updatedQuantity = existence.get().getQuantity() + quantity;
                int updatedPrice = existence.get().getPrice() + price;
                existence.get().setQuantity(updatedQuantity);
                existence.get().setPrice(updatedPrice);
            }

            // 장바구니에 존재하지 않는 새로운 옵션인 경우
            else {
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    @Transactional
    public CartResponse.UpdateDTO Update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
            System.out.println(cartList.size());
            if (cartList.size() == 0) throw new Exception400("장바구니에 구매할 물건이 없습니다: ");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
            Long totalCount = requestDTOs.stream().map(CartRequest.UpdateDTO::getCartId).count();
            Long uniqueCount = requestDTOs.stream().map(CartRequest.UpdateDTO::getCartId).distinct().count();
            if (uniqueCount < totalCount) throw new Exception400("중복 변경 요청이 존재합니다 : ");

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        for (CartRequest.UpdateDTO request : requestDTOs) {
            List<Integer> cartIds = cartList.stream().map(Cart::getId).collect(Collectors.toList());
            if (!cartIds.contains(request.getCartId())) throw new Exception400("주문 내용을 갱신할 상품이 장바구니에 존재하지 않습니다: ");

            for (Cart cart : cartList) {
               if ( cart.getId() == request.getCartId() )
                   cart.update(request.getQuantity(), cart.getOption().getPrice() * request.getQuantity());
            }
        }
        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.

        return new CartResponse.UpdateDTO(cartList);

        // 원래 내가 짜려했던 코드
        // 쿼리를 한번만 날리고 Java Spring에서 연산을 많이하는 코드가
        // 쿼리를 두개 날리는 것보다 더 효율적인가? 싶어서 퇴장
//        for (CartRequest.UpdateDTO request : requestDTOs) {
//            Optional<Cart> existence = cartJPARepository.findByUserIdCartId(user.getId(), request.getCartId());
//        }
    }
}
