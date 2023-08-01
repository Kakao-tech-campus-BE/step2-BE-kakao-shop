package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;


    //메서드 분리
    //고차 함수로 DTO내에 중복 id 를 체크하였다.
    private <T> void validCartIdDupl(List<T> dtos, Function<T, Integer> idExtractor) {
        Set<Integer> uniqueIds = dtos.stream()
                .map(idExtractor)
                .collect(Collectors.toSet());
        if (uniqueIds.size() != dtos.size()) {
            throw new IllegalArgumentException("중복된 id가 존재합니다.");
        }
    }

    private Option validAndGetOption(int optionId) {
        return optionJPARepository.findById(optionId)
                .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
    }

    private void validateCartConditions(List<Cart> cartList, List<CartRequest.UpdateDTO> requestDTOs) {
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다!");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        validCartIdDupl(requestDTOs, CartRequest.UpdateDTO::getCartId);

        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        Set<Integer> cartIds = cartList.stream().map(Cart::getId).collect(Collectors.toSet());
        for (CartRequest.UpdateDTO dto : requestDTOs) {
            if (!cartIds.contains(dto.getCartId())) {
                throw new Exception404("유저의 장바구니에 없는 cartId가 있습니다: " + dto.getCartId());
            }
        }
    }

    //제공해주신 코드를 베이스로 짠 코드
    @Transactional
    public void addCartListLecture(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

    /*1. 동일한 옵션이 들어오면 예외처리
         [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
         간단하게 해시셋하고 리스트의 크기를 비교하면 중복 여부를 확인 가능하다 -> 중복되지 않은 물건이 있어도 전체를 예외처리 할것이기 때문에*/
        validCartIdDupl(requestDTOs, CartRequest.SaveDTO::getOptionId);

     /*2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
         아래에서 같이 구현하였음 결국에는 담기 라는 기능은 동일하다고 생각하였음*/
        //3. [2번이 아니라면] 유저의 장바구니에 담기(과제 요구대로 구현)

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            // 옵션 조회
            Option option = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));

            // 유저의 장바구니에서 해당 옵션의 항목 조회
            Optional<Cart> existingCartOpt = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());
            Cart existingCart = existingCartOpt.get();

            // 장바구니에 이미 해당 옵션이 있는 경우 수량 업데이트
            if (!existingCartOpt.isEmpty()) {
                existingCart.update(existingCart.getQuantity() + quantity, option.getPrice() * existingCart.getQuantity());
            } else {
                int price = option.getPrice() * requestDTO.getQuantity();
                // 장바구니에 해당 옵션이 없는 경우 새 항목 추가
                Cart cart = Cart.builder().user(sessionUser)
                        .option(option)
                        .quantity(quantity)
                        .price(price)
                        .build();
                cartJPARepository.save(cart);
            }
        }
    }

    // 장바구니를 순회하면서 업데이트 될 객체만 가져오는 방식
    @Transactional
    public void addOrUpdateCart(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        validCartIdDupl(requestDTOs, CartRequest.SaveDTO::getOptionId);

        // 1. 한번에 여러 항목을 가져오는 조회 방식
        Set<Integer> optionIds = requestDTOs.stream().map(CartRequest.SaveDTO::getOptionId).collect(Collectors.toSet());
        List<Option> options = optionJPARepository.findAllById(optionIds);
        Map<Integer, Option> optionMap = options.stream().collect(Collectors.toMap(Option::getId, Function.identity()));

        List<Cart> existingCarts = cartJPARepository.findByOptionIdInAndUserId(optionIds, sessionUser.getId());
        Map<Integer, Cart> cartMap = existingCarts.stream().collect(Collectors.toMap(cart -> cart.getOption().getId(), cart -> cart));

        // 2. DTOs 순회 및 장바구니 처리
        List<Cart> cartsToSave = new ArrayList<>();
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Option option = optionMap.get(requestDTO.getOptionId());

            if (option == null) {
                throw new Exception404("해당 옵션을 찾을 수 없습니다 : " + requestDTO.getOptionId());
            }

            Cart existingCart = cartMap.get(requestDTO.getOptionId());
            if (existingCart != null) {
                existingCart.update(
                        existingCart.getQuantity() + requestDTO.getQuantity(),
                        existingCart.getPrice() * (existingCart.getQuantity() + requestDTO.getQuantity()));
            } else {
                int price = option.getPrice() * requestDTO.getQuantity();
                Cart cart = Cart.builder()
                        .user(sessionUser)
                        .option(option)
                        .quantity(requestDTO.getQuantity())
                        .price(price)
                        .build();
                cartsToSave.add(cart);
            }
        }

        // 3. 한번에 저장
        if (!cartsToSave.isEmpty()) {
            cartJPARepository.saveAll(cartsToSave);
        }
    }


    //장바구니 전체를 가져오는 방식
    //쿼리가 여러번 날아간다. 하지만 장바구니 수량 수정이 잦은 사용자에게는 Hash 특성으로 더 효율적일듯하다
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {


        /*강사님이 예시를 들어준 방식 = 각 id 마다 체킹을 한다.
        1. id 를 가져와 업데이트를 하면 데이터베이스 I/O가 많지 않을까?
            따라서 전체를 가져와서 Map 형태로 변환후에 업데이트/추가를 진행했다.
            -> 또 생각을 하다 보니 사람들은 기존 장바구니를 수정하는 것보다 새로 담는 경우가 많기에 id 별로 서칭을 하는것이 더 좋나? 생각이 든다.

        2. 지금 price를 확정하면 구매시점에 가격이 달라진다면 정확성에 문제가 있지 않을까? (추후 할인쿠폰 등도 고려)
            따라서 add시에 가격을 저장하지 않고 카트를 열람시에 계산하도록 설계하려 하였음
            db부하가 심하지 않을까 걱정되어 주문 시점에 한번더 계산하는 로직으로 변경*/

    //3. [2번이 아니라면] 유저의 장바구니에 담기

        //1. 전체를 가져왔다.
        List<Cart> userCarts =cartJPARepository.findAllByUserId(sessionUser.getId());

        // 2. 전체 조회 결과를 Map 형태로 변환
        Map<Integer, Cart> cartMap = userCarts.stream()
                .collect(Collectors.toMap(cart -> cart.getOption().getId(), cart -> cart));

        // 3. requestDTOs 순회 및 장바구니 처리
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Option optionPS = validAndGetOption(requestDTO.getOptionId());

            Cart existingCart = cartMap.get(requestDTO.getOptionId());
            if (existingCart != null) {
                existingCart.update(
                        existingCart.getQuantity() + requestDTO.getQuantity(),
                        existingCart.getPrice() + optionPS.getPrice() * requestDTO.getQuantity());
            } else {
                int price = optionPS.getPrice() * requestDTO.getQuantity();
                Cart cart = Cart.builder().user(sessionUser).option(optionPS)
                        .quantity(requestDTO.getQuantity()).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {
        List<Cart> cartList = cartJPARepository.findAllByUserId(user.getId());
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리
        validateCartConditions(cartList,requestDTOs);
        // 위에 3개를 처리하지 않아도 프로그램은 잘돌아간다. 예를 들어 1번을 처리하지 않으면 for문을 돌지 않고, cartList가 빈배열 []로 정상응답이 나감.
        /* 과제 예제 코드
        for (Cart cart : cartList) {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                if (cart.getId() == updateDTO.getCartId()) {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }*/
        // Map 구조를 사용하여 더 효율적으로 처리
        Map<Integer, CartRequest.UpdateDTO> updateDTOMap = requestDTOs.stream()
                .collect(Collectors.toMap(CartRequest.UpdateDTO::getCartId, Function.identity()));

        /*개수를 0으로 업데이트 하였을시에는 삭제가 되도록 추가해보았다.
            생각해보면 따로 delete 기능을 구현하는것이 더 편할것 같긴하다.
        일반적인 for-each loop에서는 컬렉션의 요소를 직접 제거할 때 ConcurrentModificationException이 발생할 수 있기에
        컬렉션을 순회하면서 해당 컬렉션의 요소를 안전하게 제거하기 위해서 Iterator을 사용해보았다.*/
        for (Iterator<Cart> iterator = cartList.iterator(); iterator.hasNext();) {
            Cart cart = iterator.next();
            if (updateDTOMap.containsKey(cart.getId())) {
                CartRequest.UpdateDTO updateDTO = updateDTOMap.get(cart.getId());
                if (updateDTO.getQuantity() == 0) {
                    iterator.remove();  // cartList에서 항목 제거
                    cartJPARepository.delete(cart);  // DB에서 항목 제거
                } else {
                    cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
                }
            }
        }
        return new CartResponse.UpdateDTO(cartList);
    } // 더티체킹
    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.

        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다!");
        }
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        // 1. 유저 장바구니에 아무것도 없으면 예외처리
        if (cartList.isEmpty()) {
            throw new Exception404("장바구니가 비어있습니다!");
        }
        return new CartResponse.FindAllDTOv2(cartList);
    }

}
