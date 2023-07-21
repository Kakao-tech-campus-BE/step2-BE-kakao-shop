package com.example.kakao.cart;

import com.example.kakao._core.utils.FakeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@RequiredArgsConstructor
@Service
public class CartService {
    private final FakeStore fakeStore;
    private final CartJPARepository cartJPARepository;


}
