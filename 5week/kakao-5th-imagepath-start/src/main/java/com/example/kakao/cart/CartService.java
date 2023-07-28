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
        // [ { optionId:1, quantity:5 }, { optionId:1, quantity:10 } ]
        // 애초에 정상적인 접근이 아닌데 이걸 처리를 해줘야 하나...?
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

        // 이거 bulk delete bulk save 를 통해 최대한 쿼리 개수를 줄여보자 -> 예외가 빵빵 터진다.
        // 예외는 em.flush() 를 통해 해결, 그런데 생각해보니 cart는 IDENTITY 전략을 사용한다... 그래서 bulk delete는 되어도 bulk insert는 안된다.....
        // 따라서 그냥 dirty checking을 통한 update 쿼리를 여러 번 날리거나, 아니면 JDBC템플릿을 직접 사용해야 한다.

        // 여기서 이런 식으로 복사하면 Cart의 깊은 복사가 되지 않는다. 따라서 이 상태로 실행하면 delete 후 삽입하는 과정에서 큰 문제가 생길 것이다.
        // List<Cart tempCarts = new ArrayList<>(userOwnCarts)
       /* List<Cart> tempCarts = new ArrayList<>(
                userOwnCarts.stream()
                        .map(cart -> Cart.builder()
                                .id(cart.getId())
                                .quantity(cart.getQuantity())
                                .price(cart.getPrice())
                                .option(cart.getOption())
                                .user(cart.getUser())
                                .build())
                        .collect(Collectors.toList())
        );
        if(!tempCarts.isEmpty()){
            for(Cart cart : tempCarts){
                int optionId = cart.getOption().getId();
                CartRequest.SaveDTO tempSaveDTO = requestDTOs.
                        stream().filter(saveDTO -> saveDTO.getOptionId() == optionId).findFirst().get();
                // 여기서 삭제해 줌으로써 아래에서 겹치지 않은 상품에 대한 처리만 하게 둔다.
                requestDTOs.remove(tempSaveDTO);
                int quantity = tempSaveDTO.getQuantity();
                int price = cart.getOption().getPrice();
                quantity += cart.getQuantity();
                cart.update(quantity, price * quantity);
            }
            // bulk delete bulk insert를 통해 쿼리 개수를 최대한 줄였다.
            // 근데 이 과정에서 cartId가 바뀌는게 맞을까?
            cartJPARepository.deleteAll(userOwnCarts);
            em.flush(); // 역시 이게 문제였다. 실제 DB에 반영이 되지 않은 상태로 자꾸 우겨넣으려고 하니 제약조건 오류가 난 것이다.
            // 어차피 IDENTITY 전략이라 하나씩 쿼리가 나간다... 이럴거면 그냥 얌전히 JDBC를 쓰거나 dirty checking을 통한 update를 쓰는 게 낫다.
            cartJPARepository.saveAll(tempCarts);
        }*/


        // 3. [2번이 아니라면] 유저의 장바구니에 담기
        // 이것도 쿼리가 requestDTO에 있는 옵션 개수만큼 나가는데 맘에 안든다. -> 옵션의 무결성 검사를 위한 과정인데..?
        // bulk select로 받아오고 리스트의 크기 검사를 통해 id 의 리스트와 실제 option의 리스트 크기가 다를 때
        // 그대로 터트려버리는 방법도 존재할 것이다.... 그러면 어떤 이상한 옵션을 넣었는지에 대한 정보가 나오지 않을 것이다. -> trade-off라고 생각하자.

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

        /*for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            int optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Option optionPS = optionJPARepository.findById(optionId)
                    .orElseThrow(() -> new Exception404("해당 옵션을 찾을 수 없습니다 : " + optionId));
            int price = optionPS.getPrice() * quantity;
            Cart cart = Cart.builder().user(sessionUser).option(optionPS).quantity(quantity).price(price).build();
            cartJPARepository.save(cart);
        }*/
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
        // 위에 save랑 DTO만 다르고 로직은 동일한데 흠... 이거하나 하자고 인터페이스 하나 선언해서 처리..?
        // 나중에 DTO가 어떻게 변경될지 모르는데 일단 별개의 로직으로 간주하자.
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
        // update는 add와 다르게 일부만 들어올 수 있다. 그러니까 저 cartList의 size와 requestDTO의 size가 다를 수 있다는 말이다.
        // 쿼리를 한번 더 날리면 되지만 그건 또 싫다. 그냥 여기서 처리하자. for문이 아무리 느려봐야 DB와의 통신보단 빠를 것이다.
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
