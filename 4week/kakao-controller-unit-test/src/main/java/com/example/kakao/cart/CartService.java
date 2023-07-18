package com.example.kakao.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, int userId) {
        //todo 옵션이 이미 있다면 수량추가
    }

    public CartResponse.FindAllDTO findAll(int userId) {
        return null;
    }

    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, int userId) {
        //todo 카트번호가 본인의 것이 맞는지 체크해야함(서비스에서)

        return null;
    }

    public void clear(int userId) {
    }
}
