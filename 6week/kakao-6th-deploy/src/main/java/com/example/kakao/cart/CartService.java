package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public List<CartRequest.SaveDTO> addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        HashSet<CartRequest.SaveDTO> removeDuplicates = new HashSet<>(requestDTOs);
        if (removeDuplicates.size() != requestDTOs.size()) {
            throw new Exception400("동일한 옵션을 처리할 수 없습니다");
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회
        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        List<CartRequest.SaveDTO> saveInfos = new ArrayList<>();

        int userId = sessionUser.getId();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int inputQuantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(optionId, userId);
            Cart cart = cartOP.orElse(Cart.builder().user(sessionUser).option(optionPS).build());
            int UpdatedQuantity = inputQuantity + cartOP.map(Cart::getQuantity).orElse(0);
            int price = optionPS.getPrice() * UpdatedQuantity;
            cart.update(UpdatedQuantity, price);

            cartJPARepository.save(cart);

            CartRequest.SaveDTO saveInfo = new CartRequest.SaveDTO();
            saveInfo.setOptionId(cart.getOption().getId());
            saveInfo.setQuantity(cart.getQuantity());
            saveInfos.add(saveInfo);
        }
        return saveInfos;
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) throw new Exception400("장바구니가 비어있어 수정할 수 없습니다.");

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        HashSet<CartRequest.UpdateDTO> removeDuplicates = new HashSet<>(requestDTOs);
        if (removeDuplicates.size() != requestDTOs.size()) {
            throw new Exception400("잘못된 접근입니다. 동일한 장바구니 아이디는 중복해서 들어올 수 없습니다.");
        }

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        List<Long> userCartIds = cartList.stream().mapToLong(Cart::getId).boxed().collect(Collectors.toList());
        boolean areAllRequestsValidCartId = requestDTOs.stream().mapToLong(CartRequest.UpdateDTO::getCartId).allMatch(userCartIds::contains);
        if (!areAllRequestsValidCartId) throw new Exception400("잘못된 접근입니다. 해당 유저가 가지고 있지 않는 장바구니에 접근할 수 없습니다.");

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
