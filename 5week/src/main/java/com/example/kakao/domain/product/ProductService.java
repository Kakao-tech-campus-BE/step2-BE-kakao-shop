package com.example.kakao.domain.product;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.product.option.Option;
import com.example.kakao.domain.product.option.OptionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductJPARepository productRepository;
  private final OptionJPARepository optionRepository;

  private static final int PAGE_SIZE = 9;

  List<ProductResponse.FindAllDTO> findAll(int page) {
    Pageable pageable = PageRequest.of(page, PAGE_SIZE);

    Page<Product> pageContent = productRepository.findAll(pageable);

    return pageContent.getContent().stream()
      .map(ProductResponse.FindAllDTO::new)
      .collect(Collectors.toList());
  }

  public ProductResponse.FindByIdDTO findById(int id) {
    // Option 이 없는 Product 도 존재할 수 있고 판매가 가능하다. -> Product 가 price 를 가지고 있기 때문.
    Product product = productRepository.findById(id).orElseThrow(
      () -> new BadRequestException("해당 상품이 존재하지 않습니다.")
    );

    List<Option> optionList = optionRepository.findAllByProductId(id);

    return new ProductResponse.FindByIdDTO( product, optionList );
  }

}
