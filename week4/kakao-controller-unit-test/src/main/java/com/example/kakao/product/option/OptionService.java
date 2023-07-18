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

    public List<ProductResponse.FindByIdDTO.OptionDTO> findAllByProductId(int id) {
        return optionJPARepository.findByProductId(id)
                .stream()
                .map(ProductResponse.FindByIdDTO.OptionDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponse.FindByIdDTO.OptionDTO save(OptionRequest.Insert request) {
        Option option = Option.builder()
                .product(request.getProduct())
                .optionName(request.getName())
                .price(request.getPrice())
                .build();
        Option savedOption = optionJPARepository.save(option);

        return new ProductResponse.FindByIdDTO.OptionDTO(savedOption);
    }

    public List<ProductResponse.FindByIdDTO.OptionDTO> saveAll(List<OptionRequest.Insert> requests) {
        List<Option> options = requests
                .stream()
                .map(request -> Option.builder()
                        .product(request.getProduct())
                        .optionName(request.getName())
                        .price(request.getPrice())
                        .build())
                .collect(Collectors.toList());

        return optionJPARepository.saveAll(options)
                .stream()
                .map(ProductResponse.FindByIdDTO.OptionDTO::new)
                .collect(Collectors.toList());
    }
}
