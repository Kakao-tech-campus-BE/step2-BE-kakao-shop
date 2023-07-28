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

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        validateDuplicateOptions(requestDTOs);

        // 2. 수량 음수 체크 -> 왜 positive 에너테이션을 걸어줬는데 음수가 그대로 값에 들어가는지?
        validateQuantityForOptionId(requestDTOs);

        // 3. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        // userId 와 optionId 를 가져온 후, 만약 현재 존재하는 id 일 경우 업데이트
        int sessionUserId = sessionUser.getId();

        for (CartRequest.SaveDTO requestDTO : requestDTOs)
        {
            // optionId 가져오기
            int optionId = requestDTO.getOptionId();
            // 수량
            int quantity = requestDTO.getQuantity();
            // option
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            // 현재 카트에 동일한 옵션 id 가 존재하는지 체크 -> optional 로 null 인지 아닌지 구분하여 if 문으로 비교하는 것이 더 좋은지, 아니면 다른 방법이 있는지?
            Optional<Cart> cartOptional = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUserId); // 여기서도 n+1 문제 발생

            // 값이 존재할 경우 ( 동일한 옵션 id 가 존재할 경우 )
            if(cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                // 수량 업데이트
                int updatedQuantity = cart.getQuantity() + requestDTO.getQuantity();
                // 가격 업데이트
                int updatedPrice = updatedQuantity * optionPS.getPrice();
                cart.update(updatedQuantity, updatedPrice);

            }
            else {
                // 4. [2번이 아니라면] 유저의 장바구니에 담기
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }

        }

    }

    // 같은 옵션 id 가 들어가있는지 체크
    @Transactional
    public void validateDuplicateOptions(List<CartRequest.SaveDTO> requestDTOs) {
        Set<Integer> optionSet = new HashSet<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            if (optionSet.contains(optionId)) {
                throw new Exception400("동일한 옵션을 추가할 수 없습니다. optionId : " + requestDTO.getOptionId());
            }
            optionSet.add(optionId);
        }
    }

    // 수량 음수 체크
    public void validateQuantityForOptionId(List<CartRequest.SaveDTO> requestDTOs) {
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            if (requestDTO.getQuantity() < 0) {
                throw new Exception400("수량은 0 이상이어야 합니다. quantity : " + requestDTO.getQuantity());
            }
        }
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
        if(cartList.isEmpty())
        {
            throw new Exception400("비어있음");
        }

        // 2. 수량 음수체크
        validateQuantityForCartIds(requestDTOs);

        // 3. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        validateDuplicateCartIds(requestDTOs);

        // 4. 유저 장바구니에 없는 cartId가 들어오면 예외처리 -> 이런 부분은 따로 함수로 빼는게 좋을지?
        List<Integer> existingCartIds = cartList.stream().map(Cart::getId).collect(Collectors.toList());

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            int cartId = requestDTO.getCartId();
            if (!existingCartIds.contains(cartId)) {
                throw new Exception404("유저 장바구니에 존재하지 않는 아이디입니다. cartId : " + cartId);
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

    // 카트옵션 체크
    private void validateDuplicateCartIds(List<CartRequest.UpdateDTO> requestDTOs) {
        Set<Integer> cartIdsSet = new HashSet<>();
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            int cartId = updateDTO.getCartId();
            if (cartIdsSet.contains(cartId)) {
                throw new Exception400("동일한 카트옵션을 추가할 수 없습니다. cartId : " + updateDTO.getCartId());
            }
            cartIdsSet.add(cartId);
        }
    }

    private void validateQuantityForCartIds(List<CartRequest.UpdateDTO> requestDTOs) {
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            if (updateDTO.getQuantity() < 0) {
                throw new Exception400("수량은 0 이상이어야 합니다. quantity : " + updateDTO.getQuantity());
            }
        }
    }
}


