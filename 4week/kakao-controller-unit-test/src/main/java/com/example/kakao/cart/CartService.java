package com.example.kakao.cart;

import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;

import java.util.List;

public interface CartService {

    public abstract void addCartList(List<CartRequest.SaveDTO> requestDTOs, User user);
    public abstract CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user);
    public abstract CartResponse.FindAllDTO findAll(User user);
    public abstract void clear(User user);
}
