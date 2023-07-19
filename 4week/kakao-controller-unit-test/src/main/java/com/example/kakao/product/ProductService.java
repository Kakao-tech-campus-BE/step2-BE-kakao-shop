package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final OptionJPARepository optionJPARepository;
    private final ProductJPARepository productJPARepository;

    public ProductService(OptionJPARepository optionJPARepository, ProductJPARepository productJPARepository) {
        this.optionJPARepository = optionJPARepository;
        this.productJPARepository = productJPARepository;
    }


    public List<ProductResponse.FindAllDTO> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 9);
        List<Product> products = productJPARepository.findAll(pageRequest).getContent();
        List<ProductResponse.FindAllDTO> findAllDTOs = new ArrayList<>();
        for(Product product:products){
            findAllDTOs.add(new ProductResponse.FindAllDTO(product));
        }
        return findAllDTOs;
    }

    public ProductResponse.FindByIdDTO findById(int productId){
        List<Option> options = optionJPARepository.mFindByProductId(productId);
        if (options.isEmpty()){return null;} // 옵션이 없다면 널값 반환 todo 어떤식으로 넘길지 고민해보기
        return new ProductResponse.FindByIdDTO(options.get(0).getProduct(),options);

    }

}
