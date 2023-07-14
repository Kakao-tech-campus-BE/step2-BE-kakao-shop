package com.example.kakao.option;

import com.example.kakao._core.utils.BeanUtils;
import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.Optional;

@Component
public class OptionListener {
    @PostPersist
    @PostUpdate
    public void prePersistAndPreUpdate(Option option) {
        System.out.println("postOption");
        ProductJPARepository productJPARepository = BeanUtils.getBean(ProductJPARepository.class);
        Optional<Integer> minPrice = option.getProduct()
                .getOptions()
                .stream()
                .map(Option::getPrice)
                .min(Integer::compare);

        System.out.println("save product");
        if (minPrice.isEmpty() || option.getPrice() < minPrice.get()) {
            System.out.println("get product");
            Product product = option.getProduct();
            System.out.println("set price");
            product.setPrice(option.getPrice());
            System.out.println("save product");
            productJPARepository.save(product);
        }
        System.out.println("end of post");
    }

}

