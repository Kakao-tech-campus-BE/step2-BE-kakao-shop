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
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final OptionJPARepository optionJPARepository;
    private final CartJPARepository cartJPARepository;

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

        requestDTOs.forEach(requestDTO -> {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option option = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다. : " + optionId));

            // 장바구니에 담겼는지 확인하는 로직
            Cart prevCart = cartJPARepository.findById(optionId).orElse(null);
            if (prevCart == null) {
                int price = option.getPrice() * quantity;
                Cart cart = Cart.builder()
                        .user(sessionUser)
                        .option(option)
                        .quantity(quantity)
                        .price(price)
                        .build();
                cartJPARepository.save(cart);
            } else {
                int updatedQuantity = quantity + prevCart.getQuantity();
                int price = option.getPrice() * updatedQuantity;

                prevCart.update(updatedQuantity, price);
            }
        });
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> carts = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
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

        // 기존 장바구니와 다른 요청이 있는지 검사(under + over 모두 검사)
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());
        if (carts.isEmpty()) {
            throw new Exception400("잘못된 요청입니다.");
        }
        Set<Integer> cartIds = carts.stream()
                .map(x->x.getId())
                .collect(Collectors.toSet());
        Set<Integer> intersection = new HashSet<>(requestIds);
        intersection.retainAll(cartIds);
        requestIds.removeAll(intersection);
        cartIds.removeAll(intersection);

        if (!requestIds.isEmpty() | !cartIds.isEmpty()){
            throw new Exception400("잘못된 요청입니다.");
        }
        
        // 메인 로직
        carts.forEach(cart -> {
            requestDTOs.forEach(requestDTO -> {
                if (cart.getId() == requestDTO.getCartId()) {
                    cart.update(requestDTO.getQuantity(), cart.getOption().getPrice() * requestDTO.getQuantity());
                }
            });
        });

        return new CartResponse.UpdateDTO(carts);
    }
}
