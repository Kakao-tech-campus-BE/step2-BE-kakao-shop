package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
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

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
            Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            // 장바구니에 담는 과정 중에 유저의 장바구니에 존재하는지를 테스트한다. for문을 돌려 각각 optionId별로 체크해야 하기 때문에 동시에 진행이 가능할 것 같았다.
            // for문이 1/2면 좋을 듯? 하다
            if (cartOP.isPresent()){
                //장바구니에 존재한다면 update로직을 수행하고 for문을 pass한다. 그렇게 하면 모든 optionId에 대해 하면서 아래의 로직을 생략할 수 있다.
                cartOP.get().update(requestDTO.getQuantity(), cartOP.get().getPrice() * quantity);
                //update 후 for문 건너뛰기
                continue;
            }
            //만약에 존재하지 않았을 경우 update가 아닌 추가 insert로직 수행
            //option이 실제 존재하지 않는 옵션일 경우의 예외 처리를 하고, 아니면 insert. option전체 중에 체크를 해야하므로 어쩔 수 없이 쿼리를 하나 더 뽑는다.
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
            cartJPARepository.save(cart);
        }
    }

    //user 본인의 카트를 조회하는 service. 카트와 상품의 정보까지 담겨있다.
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    //update로직은 Transactional을 통해 update
    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.size() == 0){
            throw new Exception400("장바구니에 상품이 존재하지 않습니다.");
        }
        // 2번은 컨트롤러로 이사감.

        //for문의 순서를 바꿔 updateDTO를 앞에두면 1번 업데이트DTO가 CARTLIST를 순회한다. 그리고 일치하는게 카트리스트에 없다면 마지막 if문으로 예외처리를 하고
        //일치하는게 존재한다면 break를 통해 더 반복하지 않고, 다음 업데이트를 진행한다.
        for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
            boolean validObject = false;
            for (Cart cart : cartList) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                    validObject = true;
                    break; // 해당 updateDTO에 대한 처리를 완료하고 더 이상 반복할 필요가 없음. 유효한 객체인가?에 true로 변경해놓고 break
                }
                //만약에 끝까지 id가 같은 카트를 찾지 못한 경우에 예외처리
                if(validObject == false){
                    throw new Exception400("업데이트할 장바구니가 존재하지 않습니다.");
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
}
