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
        List<Integer> numList = requestDTOs.stream()
                .map(x->x.getOptionId())
                .collect(Collectors.toList());
        // Set 으로 변환
        Set<Integer> numSet = new HashSet<>(numList);


        if(numSet.size()!= numList.size()){
            throw new Exception400("잘못된 요청입니다.");
        }

        if (requestDTOs.stream()
                .filter(x->x.getOptionId()<=0 | x.getQuantity()<=0)
                .collect(Collectors.toList()).size()!=0) {
            throw new Exception400("잘못된 요청입니다.");
        }

//        List<Option> options = new ArrayList<>();
        List<Option> options = requestDTOs.stream().map(x-> {
            Option option = optionJPARepository.findById(x.getOptionId())
                    .orElseThrow(() -> new Exception400("잘못된 요청입니다."));
            return option;
        }).collect(Collectors.toList());
        List<Integer> distinctProductIds = options.stream()
                .map(option -> option.getProduct().getId())
                .distinct()
                .collect(Collectors.toList());
        if (distinctProductIds.size()!=1){
            throw  new Exception400("중복되지않는 제품입니다.");
        }

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
            cartJPARepository.save(cart);
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> carts = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(carts);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        // 빈 요청 검사
        if (requestDTOs.isEmpty()) {
            throw new Exception400("잘못된 요청입니다.");
        }

        // 중복 데이터 검사
        Set<Integer> requestIds = requestDTOs.stream()
                .map(x->x.getCartId())
                .collect(Collectors.toSet());
        if (requestDTOs.size() != requestIds.size()) {
            throw new Exception400("잘못된 요청입니다.");
        }

        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());
        if (carts.isEmpty()) {
            throw new Exception400("잘못된 요청입니다.");
        }


        carts.forEach(cart -> {
            requestDTOs.forEach(requestDTO -> {
                if (cart.getId() == requestDTO.getCartId()) {
                    cart.update(requestDTO.getQuantity(), cart.getOption().getPrice() * requestDTO.getQuantity());
                }
            });
        });

        return new CartResponse.UpdateDTO(carts);
    } // 더티체킹
}
