package com.example.kakao.domain.product;

import com.example.kakao._core.errors.exception.BadRequestException;
import com.example.kakao.domain.product.option.OptionJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // 현재 앱 시작시 Bean 으로 DummyData 가 Insert 되는 중이라서 그것을 지우지않고 활용하기로 했습니다.
class ProductJPARepositoryTest {

  @Autowired
  private ProductJPARepository productRepository;

  @Autowired
  private OptionJPARepository optionRepository;

  private final static int PAGE_SIZE = 9;

  @Test
  @DisplayName("상품 전체 조회")
  void findAllProducts() {
    // given
    int page = 0;

    // when
    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    Page<Product> pageContent = productRepository.findAll(pageable);

    List<ProductResponse.FindAllDTO> productList = productRepository.findAll(pageable).stream()
      .map(ProductResponse.FindAllDTO::new)
      .collect(Collectors.toList());

    // then
    assertThat(productList).hasSize(9);
    assertThat(productList.get(0).getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    assertThat(productList.get(0).getImage()).isEqualTo("/images/1.jpg");
    assertThat(productList.get(0).getPrice()).isEqualTo(1000);
  }

  @Test
  @DisplayName("상품 상세 조회")
  void findProductById() {
    // given
    int id = 1;

    // when
    ProductResponse.FindByIdDTO product = new ProductResponse.FindByIdDTO(
      productRepository.findById(id).orElseThrow(() -> new BadRequestException("해당 상품이 존재하지 않습니다.") ),
      optionRepository.findAllByProductId(id)
    );

    // then
    assertThat(product.getProductName()).isEqualTo("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전");
    assertThat(product.getImage()).isEqualTo("/images/1.jpg");
    assertThat(product.getPrice()).isEqualTo(1000);
    assertThat(product.getOptions()).hasSize(5);
    assertThat(product.getOptions().get(0).getOptionName()).isEqualTo("01. 슬라이딩 지퍼백 크리스마스에디션 4종");
    assertThat(product.getOptions().get(0).getPrice()).isEqualTo(10000);
  }

  @Test
  @DisplayName("옵션 없는 상품 상세 조회 : 현재 약속된 명세로는 정합성이 깨진 케이스")
  void findProductWithoutOption() {
    // given
    int id = 16;

    // when
    ProductResponse.FindByIdDTO product = new ProductResponse.FindByIdDTO(
      productRepository.findById(id).orElseThrow(() -> new BadRequestException("해당 상품이 존재하지 않습니다.") ),
      optionRepository.findAllByProductId(id)
    );

    // then
    assertThat(product.getProductName()).isEqualTo("16번째 테스트 상품(옵션없음테스트용): Index(16)");
    assertThat(product.getImage()).isEqualTo("/images/16.jpg");
    assertThat(product.getPrice()).isEqualTo(20000);
    assertThat(product.getOptions()).isEmpty();
  }

  @Test
  @DisplayName("없는 상품 조회")
  void findProductNotExists() {
    // given
    int id = 0;

    // when
    Optional<Product> product = productRepository.findById(id);

    // then
    assertThat(product).isEmpty();
  }
}