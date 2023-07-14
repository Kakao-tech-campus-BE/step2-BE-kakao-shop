package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductOptionJPARepository extends JpaRepository<ProductOption, Integer> {

    // productId를 기반으로 ProductOption을 조회하는 쿼리
    // 인자로 전달된 productId 값을 사용하여 한 번의 select 문을 실행
    List<ProductOption> findByProductId(@Param("productId") int productId);

    // findById_select_product_lazy_error_fix_test
    // 해당 상품을 가지고 있는 옵션들을 가져와라
    //    @Query("SELECT p FROM Product p LEFT JOIN ProductOption o ON o.product.id = p.id WHERE p.id = :productId")
    @Query("select o from ProductOption o join fetch o.product where o.product.id = :productId")
    List<ProductOption> mFindByProductId(@Param("productId") int productId);
}
