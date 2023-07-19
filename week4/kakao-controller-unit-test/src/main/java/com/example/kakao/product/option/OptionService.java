package com.example.kakao.product.option;

import com.example.kakao.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionService {
    private final OptionJPARepository optionJPARepository;

    public void saveAll(List<OptionRequest.Insert> requests) {
        List<Option> options = requests
                .stream()
                .map(request -> Option.builder()
                        .product(request.getProduct())
                        .optionName(request.getName())
                        .price(request.getPrice())
                        .build())
                .collect(Collectors.toList());

        optionJPARepository.saveAll(options);
    }
}
