package com.example.kakao.product;

import java.util.List;

public interface ProductService {

    public abstract ProductResponse.FindByIdDTO findById(int id);
    public abstract List<ProductResponse.FindAllDTO> findAll(int page);
}
