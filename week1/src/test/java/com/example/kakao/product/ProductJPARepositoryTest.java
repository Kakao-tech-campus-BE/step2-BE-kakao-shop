package com.example.kakao.product;

import com.example.kakao.option.OptionJPARepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductJPARepositoryTest {
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;

    @Test
    void test(){
        Product product = Product.builder()
                .name("제품 A")
                .image("1.png")
                .price(1000)
                .build();

        productJPARepository.save(product);
        productJPARepository.findAll().forEach(System.out::println);
    }
}