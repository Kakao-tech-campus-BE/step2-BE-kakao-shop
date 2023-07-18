package com.example.kakao.product;

import com.example.kakao.product.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetProductUsecase {
    private final ProductService productService;
    private final OptionService optionService;

    public ProductResponse.FindByIdDTO execute(int id) {
        ProductResponse.FindAllDTO product = productService.getProduct(id);
        List<ProductResponse.FindByIdDTO.OptionDTO> options = optionService.findAllByProductId(id);

        return new ProductResponse.FindByIdDTO(product, options);
    }
}
