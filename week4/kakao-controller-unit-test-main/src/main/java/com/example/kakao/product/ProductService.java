package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OptionJPARepository optionJPARepository;

    public List<Product> saveAll(List<Product> products) {
        return productJPARepository.saveAll(products);
    }

    public void deleteAll() {
        productJPARepository.deleteAll();
    }

    public List<Product> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productJPARepository.findAll(pageRequest).stream().skip(page*size).collect(Collectors.toList());
    }

    public Optional<Product> findById(int id) {
        return productJPARepository.findById(id);
    }

    public List<Option> findOptionByProductId(int id) {
        return optionJPARepository.findByProductId(id);
    }
}
