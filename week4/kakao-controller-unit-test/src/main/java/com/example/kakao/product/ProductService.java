package com.example.kakao.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;

    public List<ProductResponse.FindAllDTO> getProducts(PageRequest pageRequest) {
        return productJPARepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponse.FindAllDTO getProduct(int id) {
        Product product = productJPARepository.findById(id).orElseThrow();

        return new ProductResponse.FindAllDTO(product);
    }

    public ProductResponse.FindAllDTO save(ProductRequest.Insert request) {
        Product product = Product.builder()
                .productName(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .price(request.getPrice())
                .build();
        Product savedProduct = productJPARepository.save(product);

        return new ProductResponse.FindAllDTO(savedProduct);
    }

    public List<ProductResponse.FindAllDTO> saveAll(List<ProductRequest.Insert> requests) {
        List<Product> products = requests
                .stream()
                .map(request -> Product.builder()
                        .productName(request.getName())
                        .description(request.getDescription())
                        .image(request.getImage())
                        .price(request.getPrice())
                        .build())
                .collect(Collectors.toList());

        return productJPARepository.saveAll(products)
                .stream()
                .map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());
    }
}
