package com.example.kakao.product;

import com.example.kakao.product.option.ProductOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductJPARepository extends JpaRepository<Product, Integer> {

    // 페이징 처리된 전체 상품 목록을 조회
    // 단일 쿼리로 모든 상품을 가져오기 때문에 한 번의 select 문을 실행
    //  JPA의 명명 규칙 : findAll이라는 키워드로 시작하고, 그 뒤에 WithPaging이라는 추가적인 정보를 포함
    @Query("SELECT p FROM Product p")
    Page<Product> findAllWithPaging(PageRequest pageRequest);

}