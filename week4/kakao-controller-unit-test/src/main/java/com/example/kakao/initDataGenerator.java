package com.example.kakao;

import com.example.kakao._core.utils.FakeStore;
import com.example.kakao.product.ProductRequest;
import com.example.kakao.product.ProductService;
import com.example.kakao.product.option.OptionRequest;
import com.example.kakao.product.option.OptionService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class initDataGenerator implements ApplicationRunner {
    private final FakeStore fakeStore;
    private final ProductService productService;
    private final OptionService optionService;

    public initDataGenerator(FakeStore fakeStore, ProductService productService, OptionService optionService) {
        this.fakeStore = fakeStore;
        this.productService = productService;
        this.optionService = optionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createProducts();
        createOptions();
    }

    private void createProducts() {
        List<ProductRequest.Insert> requests = fakeStore.getProductList().stream().map(product -> ProductRequest.Insert.builder()
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .image(product.getImage())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
        productService.saveAll(requests);
    }

    private void createOptions() {
        List<OptionRequest.Insert> optionRequests = fakeStore.getOptionList().stream().map(option -> OptionRequest.Insert.builder()
                        .product(option.getProduct())
                        .name(option.getOptionName())
                        .price(option.getPrice())
                        .build())
                .collect(Collectors.toList());
        optionService.saveAll(optionRequests);
    }
}
