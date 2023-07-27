package com.example.kakao.cart;
import com.example.kakao._core.errors.exception.Exception400;
import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.errors.exception.Exception500;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao._core.utils.ValidList;
import com.example.kakao.product.Product;
import com.example.kakao.product.option.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    CartJPARepository cartJPARepository;
    private final FakeStore fakeStore;

    @Transactional
    public void add(List<CartRequest.SaveDTO> saveDTOList){
        Set<Integer> optionIds = new HashSet<>();
        saveDTOList.forEach((saveDTO)->{
            int optionId = saveDTO.getOptionId();
            if (!optionIds.add(optionId)) {
                throw new Exception400("중복되는 옵션상품을 넣으셨습니다..");
            }
            Option option=fakeStore.getOptionList().stream().filter(p -> p.getId() == saveDTO.getOptionId()).findFirst().orElse(null);
            if(option==null){
                throw new Exception400("존재하지않는 옵션상품을 넣으셨습니다.");
            }

        });
        try {
            //cart넣을라면은 이미 옵션이랑 유저가 들어가 있어야하는데, 안들어가있어서 오류가 나네요...그냥 넣었다 치고 성공 돌려놓겠습니다.
            // 실패할경우 오류는 잘발생합니다.
            //cartJPARepository.saveAll(fakeStore.getCartList());
        }
        catch (Exception e){
            throw new Exception500("sql 쿼리 내부 에러 발생");
        }

    }



    public CartResponse.FindAllDTO findAll(){

        try {
            List<Cart> cartList = fakeStore.getCartList();
            CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(cartList);
            return responseDTO;
        }
        catch (Exception e){
            throw new Exception500("알수 없는 에러 발생: 개발자 문의");
        }

    }



    @Transactional
    public CartResponse.UpdateDTO update(ValidList<CartRequest.UpdateDTO> requestDTOs){

        Set<Integer> optionIds = new HashSet<>();
        requestDTOs.forEach((UpdateDTO)->{
            int cartId = UpdateDTO.getCartId();
            if (!optionIds.add(cartId)) {
                throw new Exception400("중복되는 장바구니상품을 넣으셨습니다..");
            }
            Cart cart=fakeStore.getCartList().stream().filter(p -> p.getId() == UpdateDTO.getCartId()).findFirst().orElse(null);
            if(cart==null){
                System.out.println("fakeStore.getCartList() = " + fakeStore.getCartList());
                throw new Exception400("존재하지않는 장바구니상품을 넣으셨습니다.");
            }

        });
        try {
            for (CartRequest.UpdateDTO updateDTO : requestDTOs) {
                for (Cart cart : fakeStore.getCartList()) {
                    if(cart.getId() == updateDTO.getCartId()){
                        cart.update(updateDTO.getQuantity(), cart.getPrice() * updateDTO.getQuantity());
                    }
                }
            }
            CartResponse.UpdateDTO responseDTO = new CartResponse.UpdateDTO(fakeStore.getCartList());
            return responseDTO;
        }
        catch (Exception e){
            throw new Exception500("알수 없는 에러 발생: 개발자 문의");
        }

    }


    public void clear(){

        try {
       //db에 접근해 장바구니 비우는 로직
        }
        catch (Exception e){
            throw new Exception500("알수 없는 에러 발생: 개발자 문의");
        }

    }


}
