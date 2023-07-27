package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {
    Optional<Option> findById(int id);

    // findById_select_product_lazy_error_fix_test
//    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
//    List<Option> findByProductId(@Param("productId") int productId);
    @EntityGraph("OptionWithProduct")
    List<Option> findByProductId(int productId);
}
