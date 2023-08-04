package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {

    private final EntityManager em;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser) {
        // 1. 동일한 옵션이 들어오면 예외처리

        List<CartRequest.SaveDTO> sortedRequestDTOs = requestDTOs.stream().sorted(Comparator.comparing(CartRequest.SaveDTO::getOptionId)).collect(Collectors.toList());
        List<Integer> optionIdList = sortedRequestDTOs.stream().map( saveDTO -> saveDTO.getOptionId()).distinct().collect(Collectors.toList());
        List<CartRequest.SaveDTO> adjustedRequestDTOs = new ArrayList<>();
        if(requestDTOs.size() > optionIdList.size()){
            int idx = 0;
            int quantitySum = 0;
            for(Integer optionId : optionIdList){
                quantitySum = 0;
                while(
                        (idx < sortedRequestDTOs.size())
                                &&
                                (sortedRequestDTOs.get(idx).getOptionId() == optionId)
                ){
                    quantitySum += sortedRequestDTOs.get(idx).getQuantity();
                    idx++;
                }
                CartRequest.SaveDTO temp = new CartRequest.SaveDTO();
                temp.setOptionId(optionId);
                temp.setQuantity(quantitySum);
                adjustedRequestDTOs.add(temp);
            }
            requestDTOs = new ArrayList<>(adjustedRequestDTOs);
        }

        // 2. cartJPARepository.findByOptionIdAndUserId() 조회 -> 존재하면 장바구니에 수량을 추가하는 업데이트를 해야함. (더티체킹하기)

        List<Cart> userOwnCarts = cartJPARepository.findAllByUserIdAndOptionIds(sessionUser.getId(), optionIdList);

        if(!userOwnCarts.isEmpty()){
            for(Cart cart : userOwnCarts){
                int optionId = cart.getOption().getId();
                optionIdList.remove(optionIdList.indexOf(optionId));
                CartRequest.SaveDTO tempSaveDTO = requestDTOs.
                        stream().filter(saveDTO -> saveDTO.getOptionId() == optionId).findFirst().get();
                // 여기서 requestDTO에 있는 겹치는 상품들을 삭제해 줌으로써 아래에서 겹치지 않은 상품에 대한 처리만 하게 둔다.
                requestDTOs.remove(tempSaveDTO);
                int quantity = tempSaveDTO.getQuantity();
                int price = cart.getOption().getPrice();
                quantity += cart.getQuantity();
                cart.update(quantity, price * quantity);
            }
        }

        if(!requestDTOs.isEmpty()){
            List<Option> optionList = optionJPARepository.findAllByIdJoinProduct(optionIdList);
            if(requestDTOs.size() != optionList.size()){
                throw new Exception404("존재하지 않는 옵션에 대한 요청이 들어왔습니다. : " + optionIdList);
            }
            List<Cart> cartSaveList = new ArrayList<>();
            // 이러면 둘의 순서가 같음이 보장된다.
            requestDTOs.sort(Comparator.comparing(CartRequest.SaveDTO::getOptionId));
            optionList.sort(Comparator.comparing(Option::getId));
            // bulk insert를 위한 Cart의 List 만들기 -> 어차피 IDENTITY 때문에 하나씩 들어감...
            for(int i = 0; i < requestDTOs.size(); i++){
                int optionId = requestDTOs.get(i).getOptionId();
                int quantity = requestDTOs.get(i).getQuantity();
                int price = optionList.get(i).getPrice() * quantity;
                cartSaveList.add(
                        Cart.builder()
                                .user(sessionUser)
                                .option(optionList.get(i))
                                .quantity(quantity)
                                .price(price)
                                .build()
                );
            }
            cartJPARepository.saveAll(cartSaveList);
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
        if(cartList.isEmpty()){
            throw new Exception400("장바구니가 비어있습니다.");
        }

        // 2. cartId:1, cartId:1 이렇게 requestDTOs에 동일한 장바구니 아이디가 두번 들어오면 예외처리
        List<CartRequest.UpdateDTO> sortedRequestDTOs = requestDTOs
                .stream()
                .sorted(Comparator.comparing(CartRequest.UpdateDTO::getCartId)).collect(Collectors.toList());

        List<Integer> cartIdList = sortedRequestDTOs
                .stream()
                .map( updateDTO -> updateDTO.getCartId()).distinct().collect(Collectors.toList());

        List<CartRequest.UpdateDTO> adjustedRequestDTOs = new ArrayList<>();
        if(requestDTOs.size() > cartIdList.size()){
            int idx = 0;
            int quantitySum = 0;
            for(Integer cartId : cartIdList){
                quantitySum = 0;
                while(
                        (idx < sortedRequestDTOs.size())
                                &&
                                (sortedRequestDTOs.get(idx).getCartId() == cartId)
                ){
                    quantitySum += sortedRequestDTOs.get(idx).getQuantity();
                    idx++;
                }
                CartRequest.UpdateDTO temp = new CartRequest.UpdateDTO();
                temp.setCartId(cartId);
                temp.setQuantity(quantitySum);
                adjustedRequestDTOs.add(temp);
            }
            requestDTOs = new ArrayList<>(adjustedRequestDTOs);
        }


        // 3. 유저 장바구니에 없는 cartId가 들어오면 예외처리

        List<Integer> userCartIdList = cartList.stream().map( cart -> cart.getId()).collect(Collectors.toList());
        for(Integer cartId : cartIdList){
            if(!userCartIdList.contains(cartId)){
                throw new Exception404("존재하지 않는 장바구니에 대한 요청이 들어왔습니다. : " + cartId);
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
}
