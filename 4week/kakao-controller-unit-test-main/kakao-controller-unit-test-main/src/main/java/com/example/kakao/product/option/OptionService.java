package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionJPARepository optionJPARepository;

    public List<Option> findAllByProduct(Product product) {
        return optionJPARepository.findAllByProduct(product);
    }
}
