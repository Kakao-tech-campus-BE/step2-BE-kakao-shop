package com.example.kakao.product;

import com.example.kakao._core.errors.exception.product.ProductException;
import com.example.kakao._core.util.DummyEntity;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@DisplayName("제품_옵션_서비스_테스트 ")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends DummyEntity{
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductJPARepository productJPARepository;

    @Mock
    private OptionJPARepository optionJPARepository;

    @DisplayName("상품_전체_조회_테스트")
    @Test
    public void product_findAll_test() {
        // given
        int page = 0;
        int pageSize = 9;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage = createPageProduct(pageable);

        // mocking
        given(productJPARepository.findAll(pageable)).willReturn(productPage);

        // when
        List<ProductResponse.FindAllDTO> resultDTOs = productService.findAll(page);

        // then
        assertThat(resultDTOs.size()).isEqualTo(pageSize);
    }

    @DisplayName("상품_상세_조회_테스트")
    @Test
    public void product_findById_test() {
        // given
        List<Product> productList = productDummyList();
        List<Option> optionList = optionDummyList(productList).stream()
                .filter(option -> option.getProduct().getProductName().equals("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"))
                .collect(Collectors.toList());

        // mocking
        given(optionJPARepository.findByProductIdJoinProduct(anyInt())).willReturn(optionList);

        // when
        ProductResponse.FindByIdDTOv2 resultDTOs = productService.findByIdv2(0);

        // then
        assertThat(resultDTOs.getOptions().size()).isEqualTo(5);
    }

    @DisplayName("상품_상세_조회_테스트_실패_상품_없음")
    @Test
    public void product_findById_fail_no_data() {
        // given
        int fakeId = 1;

        // mocking
        given(optionJPARepository.findByProductIdJoinProduct(anyInt())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> productService.findByIdv2(fakeId)).isInstanceOf(ProductException.ProductNotFoundException.class);
    }

    private Page<Product> createPageProduct(Pageable pageable) {
        List<Product> productList= productDummyList();
        return new PageImpl<>(productList.subList(0, pageable.getPageSize()), pageable, productList.size());
    }

}
