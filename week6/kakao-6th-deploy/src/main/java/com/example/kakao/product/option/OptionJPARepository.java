package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    /* 개별상품조회 개별쿼리 */
    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);

    /* 개별상품조회 한방쿼리 */
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> findByProductIdJoinProduct(@Param("productId") int productId);
}
