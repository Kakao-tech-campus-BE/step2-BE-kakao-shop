package com.example.kakao.product;

import com.example.kakao._core.errors.exception.Exception404;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<ProductResponse.FindAllDTO> findAllProducts(PageRequest pageRequest) {
        return productJPARepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponse.FindByIdDTO findProductById(int id) {
        List<Option> options = optionJPARepository.findByProductId(id);

        if (options.isEmpty()) {
            throw new Exception404("존재하지 않는 상품입니다.");
        }

        return new ProductResponse.FindByIdDTO(options.get(0).getProduct(), options);
    }

    public void saveAll(List<ProductRequest.Insert> requests) {
        List<Product> products = requests
                .stream()
                .map(request -> Product.builder()
                        .productName(request.getName())
                        .description(request.getDescription())
                        .image(request.getImage())
                        .price(request.getPrice())
                        .build())
                .collect(Collectors.toList());

        productJPARepository.saveAll(products);
    }
}
