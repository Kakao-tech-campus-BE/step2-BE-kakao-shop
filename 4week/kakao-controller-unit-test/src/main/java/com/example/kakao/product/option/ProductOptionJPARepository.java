package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


// interface 명 수정
public interface ProductOptionJPARepository extends JpaRepository<ProductOption, Integer> {

    List<ProductOption> findByProductId(@Param("productId") int productId);
    Optional<ProductOption> findById(int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select o from ProductOption o join fetch o.product where o.product.id = :productId")
    List<ProductOption> mFindByProductId(@Param("productId") int productId);
}
