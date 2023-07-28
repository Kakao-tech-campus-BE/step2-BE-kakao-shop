package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.cart.CartException;
import com.example.kakao._core.errors.exception.product.option.OptionException;
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

    // insert 쿼리 2+n | update 쿼리 2+n번 + saveAll 사용
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {

        // 1. 중복되는 옵션ID가 들어오면 예외처리를 발생시킨다.
        List<Integer> requestOptionIds = getValidDataIds(requestDTOs, CartRequest.SaveDTO::getOptionId, new OptionException.DuplicatedOptionException());

        // 2. 옵션 존재 여부 확인
        List<Option> optionList = getValidOptionList(requestOptionIds);

        // 3. 장바구니에 해당 옵션 데이터가 있는지 확인하기 위한 select 쿼리
        List<Cart> cartList = cartJPARepository.findByUserIdAndOptionIdIn(requestOptionIds, sessionUser.getId());

        // 4. 조회하기 쉽게 optionID로 그룹화 진행
        Map<Integer, Option> optionGroupByOptionId = optionList.stream()
                .collect(Collectors.toMap(Option::getId, o -> o));
        Map<Integer, Cart> cartGroupByOptionId = cartList.stream()
                .collect(Collectors.toMap(c -> c.getOption().getId(), c -> c));

        // 5. 카트에 이미 있는 옵션의 경우 업데이트, 없다면 추가
        requestDTOs.forEach( dto -> {
            if(cartGroupByOptionId.containsKey(dto.getOptionId())) {
                int newQuantity = cartGroupByOptionId.get(dto.getOptionId()).getQuantity() + dto.getQuantity();
                int newPrice = cartGroupByOptionId.get(dto.getOptionId()).getOption().getPrice() * newQuantity;
                cartGroupByOptionId.get(dto.getOptionId()).update(newQuantity, newPrice);
            } else {
                cartList.add(Cart.builder()
                        .option(optionGroupByOptionId.get(dto.getOptionId()))
                        .user(sessionUser)
                        .quantity(dto.getQuantity())
                        .price(dto.getQuantity() * optionGroupByOptionId.get(dto.getOptionId()).getPrice())
                        .build());
            }
        });

        // 6. 업데이트 및 추가한 장바구니를 디비에 저장
        // saveAll후 Flush를 해줘야한다.
        // 그렇지 않으면 update가 트랜잭션 이후에 동작하기 때문에 예외처리가 제대로 동작하지 않는다.
        try{
            cartJPARepository.saveAllAndFlush(cartList);
        } catch(Exception e) {
            throw new CartException.CartSaveException(e.getMessage());
        }
    }


    // 내가 짠 코드와 비교를 위한 코드
    // insert 쿼리 (n*3번) | update 쿼리 (n*2번) | 혼합(n + (업데이트 수) + (insert 수)*2 )
    @Transactional
    public void addCartListV2(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        long count = requestDTOs.stream()
                .mapToInt(CartRequest.SaveDTO::getOptionId).distinct().count();
        if (requestDTOs.size() != count) throw new OptionException.DuplicatedOptionException();

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)
        Set<Integer> cartSet = new HashSet<>();

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Optional<Cart> cartOP = cartJPARepository.findByOptionIdAndUserId(requestDTO.getOptionId(), sessionUser.getId());
            if(cartOP.isEmpty()) continue;

            Cart cart = cartOP.get();
            cart.update(requestDTO.getQuantity(), requestDTO.getQuantity() * cart.getOption().getPrice());
            cartSet.add(cart.getOption().getId());
            try {
                cartJPARepository.saveAndFlush(cart);
            } catch(Exception e){
                throw new CartException.CartSaveException(e.getMessage());
            }
        }

        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();

            if(cartSet.contains(optionId)) continue;

            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new OptionException.OptionNotFoundException(optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
            try {
                cartJPARepository.save(cart);
            } catch(Exception e){
                throw new CartException.CartSaveException(e.getMessage());
            }
        }
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user) {

        // 1. 중복 입력이 들어올 경우 예외처리
        List<Integer> requestCartIds = getValidDataIds(requestDTOs, CartRequest.UpdateDTO::getCartId,new CartException.DuplicatedCartException());

        // 2. 유저 장바구니에 아무것도 없으면 예외처리 & 유저 장바구니에 없는 cartId가 들어오면 예외처리
        List<Cart> cartList = getValidCartList(requestCartIds, user.getId());

        // 3. 장바구니 업데이트
        Map<Integer, Cart> cartGroupByCartId = cartList.stream()
                .collect(Collectors.toMap(Cart::getId, cart -> cart));

        requestDTOs.forEach(updateDTO -> {
            Cart cart = cartGroupByCartId.get(updateDTO.getCartId());
            cart.update(updateDTO.getQuantity(), cart.getOption().getPrice() * updateDTO.getQuantity());
        });

        // 4. 장바구니 저장
        // 더티체킹으로 update를 처리하는 것보다 직접 flush를 해주는 것이 예외처리에 도움이 될 것 같다.
        try {
            cartJPARepository.saveAllAndFlush(cartList);
        } catch(Exception e) {
            throw new CartException.CartUpdateException(e.getMessage());
        }
        return new CartResponse.UpdateDTO(cartList);
    }


    public CartResponse.FindAllDTO findAll(User user) {
        List<Cart> cartList = getValidFindAllCartList(user);

        // Cart에 담긴 옵션이 3개이면, 2개는 바나나 상품, 1개는 딸기 상품이면 Product는 2개인 것이다.
        return new CartResponse.FindAllDTO(cartList);
    }

    public CartResponse.FindAllDTOv2 findAllv2(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        return new CartResponse.FindAllDTOv2(cartList);
    }

    public void clearAll(User user) {

        List<Cart> cartList = getValidCartList(user.getId());

        try {
            cartJPARepository.deleteAllInBatch(cartList);
        } catch(Exception e) {
            throw new CartException.CartDeleteException(e.getMessage());
        }
    }

    // --------- private ---------

    // 중복되는 id가 들어오면 예외를 발생시킨다.
    private <T, E extends RuntimeException> List<Integer> getValidDataIds(List<T> requestDtos, Function<T, Integer> getIdFunc, E exception){
        List<Integer> requestIds = requestDtos.stream()
                .map(getIdFunc).distinct().collect(Collectors.toList());

        if(requestDtos.size() != requestIds.size()) {
            throw exception;
        }

        return requestIds;
    }

    /**
     * Option이 데이터베이스에 없으면 예외를 발생시킨다.
     */
    private List<Option> getValidOptionList(List<Integer> requestOptionIds) {
        List<Option> optionList = optionJPARepository.findByIdIn(requestOptionIds);
        List<Integer> notExistOptionIds = findNotExistIds(requestOptionIds, optionList.stream().map(Option::getId).collect(Collectors.toSet()));
        if (!notExistOptionIds.isEmpty())
            throw new OptionException.OptionNotFoundException(notExistOptionIds);

        return optionList;
    }

    /**
     * Cart가 데이터베이스에 없으면 예외를 발생시킨다.
     * 입력받은 CartId가 회원의 장바구니에 존재하지 않는다면 예외를 발생시킨다.
     */
    private List<Cart> getValidCartList(List<Integer> requestIds,int userId) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdFetchOption(userId);
        if(cartList.isEmpty()) throw new CartException.CartNotFoundException();

        List<Integer> notExistCartIds = findNotExistIds(requestIds, cartList.stream().map(Cart::getId).collect(Collectors.toSet()));
        if (!notExistCartIds.isEmpty()) throw new CartException.NotExistsUserCartsException(notExistCartIds);

        return cartList;
    }

    /**
     * Cart가 데이터베이스에 없으면 예외를 발생시킨다.
     */
    private List<Cart> getValidCartList(int userId) {
        List<Cart> cartList = cartJPARepository.findAllByUserIdFetchOption(userId);
        if(cartList.isEmpty()) throw new CartException.CartNotFoundException();

        return cartList;
    }

    /**
     * 데이터베이스에 있는 데이터와 입력받은 데이터 ID간의 유효성 체크
     */
    private List<Integer> findNotExistIds(List<Integer> requestIds, Set<Integer> databaseIds) {
        Set<Integer> setRequestIds = new HashSet<>(requestIds);

        setRequestIds.removeAll(databaseIds);
        return new ArrayList<>(setRequestIds);
    }

    /**
     * 회원의 장바구니에 데이터가 없다면 예외를 발생시킨다.
     */
    private List<Cart> getValidFindAllCartList(User user) {
        List<Cart> cartList = cartJPARepository.findByUserIdOrderByOptionIdAsc(user.getId());
        if(cartList.isEmpty()) throw new CartException.CartNotFoundException();
        return cartList;
    }
}
