package com.example.kakao.option;

import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OptionJPARepositoryTest {
    @Autowired
    private ProductJPARepository productJPARepository;
    @Autowired
    private OptionJPARepository optionJPARepository;

    @Test
    void test() {
        Option option = Option.builder()
                .name("옵션 A")
                .price(1000)
                .build();
        optionJPARepository.save(option);
        optionJPARepository.findAll().forEach(System.out::println);
    }

    @Test
    void saveProductandOption() {
        Product product = Product.builder()
                .name("제품 A")
                .image("1.png")
                .price(1000)
                .build();

        Option option1 = Option.builder()
                .name("옵션 A")
                .price(1000)
                .product(product)
                .build();

        Option option2 = Option.builder()
                .name("옵션 B")
                .price(100)
                .product(product)
                .build();

        productJPARepository.save(product);
        optionJPARepository.saveAll(Lists.newArrayList(option1, option2));

//        productJPARepository.findAll().forEach(System.out::println);
        optionJPARepository.findAll().forEach(System.out::println);

        optionJPARepository.findAll().forEach(x -> System.out.println(x.getProduct()));
//        optionJPARepository.findByProduct(product).forEach(System.out::println);
        System.out.println(productJPARepository.findByOptions(option1));
    }
}