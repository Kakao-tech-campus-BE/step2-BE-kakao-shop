package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    @Query("select o " +
            "from Option o " +
            "join fetch o.product " +
            "where o.id = :id")
    Optional<Option> findById(@Param("id") int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select o " +
            "from Option o " +
            "join fetch o.product " +
            "where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);

    @Modifying
    @Query("delete from Option o where o.product.id = :productId")
    void deleteByProductId(@Param("productId") int productId);
}
