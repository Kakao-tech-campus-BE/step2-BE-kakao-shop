package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCart(List<CartRequest.SaveDTO> requestDTOs, User user) {
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());
        Set<Integer> cartRepositorySet = carts.stream()
                .map(cart -> cart.getOption().getId())
                .collect(Collectors.toSet());

        //장바구니 담기 시 예외 처리를 위한 책임 분리
        addCartException(requestDTOs);

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            //장바구니에 이미 해당 옵션이 있으면 개수와 가격을 업데이트
            if (cartRepositorySet.contains(optionId)) {
                updateCartQuantity(carts, optionId, quantity, optionPS.getPrice());
            } else {    //없으면 장바구니에 담기
                int price = optionPS.getPrice() * quantity;
                Cart cart = Cart.builder().user(user).option(optionPS).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    private void addCartException(List<CartRequest.SaveDTO> requestDTOs) {
        Set<Integer> cartSet = new HashSet<>();

        //동일한 옵션을 동시에 여러번 요청할 때 예외
        //예를들면 옵션 1번을 한 DTO에 두번 요청 시 예외 처리
        for (CartRequest.SaveDTO saveDTO : requestDTOs) {
            int optionId = saveDTO.getOptionId();
            if (cartSet.contains(optionId)) {
                throw new Exception400("동일한 옵션을 여러번 요청할 수 없습니다.");
            }

            cartSet.add(optionId);
        }

        //동일한 상품에 대한 요청이 아닐 시
        //예를들면 옵션 1번과 옵션 44번은 다른 상품이므로 장바구니 담기라는 기능에는 적합하지 않은 논리적인 모순
        List<Integer> optionIds = requestDTOs.stream()
                .map(requestDTO -> requestDTO.getOptionId())
                .collect(Collectors.toList());
        Option option = optionJPARepository.findById(optionIds.get(0))
                .orElseThrow(() -> new Exception400("해당 옵션을 찾을 수 없습니다."));
        Product product = option.getProduct();

        for(Integer optionId : optionIds) {
            Option findOption = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception400("해당 옵션을 찾을 수 업습니다."));
            Product findProduct = findOption.getProduct();
            if(product != findProduct) {
                throw new Exception400("다른 상품은 장바구니에 담을 수 없습니다.");
            }
        }
    }

    private void updateCartQuantity(List<Cart> carts, int optionId, int quantity, int price) {
        for (Cart cart : carts) {
            if (cart.getOption().getId() == optionId) {
                int newQuantity = cart.getQuantity() + quantity;
                int newPrice = newQuantity * price;
                cart.update(newQuantity, newPrice);
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> carts = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTO(carts);
    }

    @Transactional
    public CartResponse.UpdateDTO updateCart(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> carts = cartJPARepository.findAllByUserId(user.getId());

        updateCartException(carts, requestDTOs);

        for (Cart cart : carts) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(carts);
    } // 더티체킹

    private void updateCartException(List<Cart> carts, List<CartRequest.UpdateDTO> requestDTOs) {
        if(carts.isEmpty()) {
            throw new Exception400("장바구니가 비어 있습니다.");
        }

        Set<Integer> cartRepositorySet = carts.stream()
                .map(Cart::getId)
                .collect(Collectors.toSet());

        Set<Integer> cartSet = new HashSet<>();

        requestDTOs.forEach(updateDTO -> {
            if (cartSet.contains(updateDTO.getCartId())) {  //같은 장바구니 ID로 여러번 요청
                throw new Exception400("해당 요청은 잘못 되었습니다.");
            }
            cartSet.add(updateDTO.getCartId());

            if (!cartRepositorySet.contains(updateDTO.getCartId())) {   //장바구니에 없는 ID의 변경 요청
                throw new Exception400("해당 상품은 장바구니에 존재하지 않습니다.");
            }
        });
    }
}
