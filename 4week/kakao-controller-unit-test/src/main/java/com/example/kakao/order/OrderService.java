package com.example.kakao.order;

import com.example.kakao.user.User;

public interface OrderService {
    public abstract OrderResponse.FindByIdDTO saveOrder(User user);
    public abstract OrderResponse.FindByIdDTO findById(int id);
    public abstract void clear();
}
