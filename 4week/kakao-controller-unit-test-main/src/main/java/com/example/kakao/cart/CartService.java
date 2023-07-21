package com.example.kakao.cart;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final FakeStore fakeStore;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public Option findOptionId(int id){
//        fakeStore.getOptionList().stream().filter(o -> o.getId() == id).findFirst().orElseThrow(
//                () -> new Exception404("해당 옵션을 찾을 수 없습니다:"+id)
//        );
        return optionJPARepository.findById(id).orElseThrow(
                () -> new Exception404("해당 옵션을 찾을 수 없습니다:"+id)
        );
    }
}
