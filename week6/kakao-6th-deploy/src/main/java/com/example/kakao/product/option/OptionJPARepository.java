package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);

    //    @Query(value = "select * from option_tb o inner join product_tb p on o.product_id = p.id where o.product_id = :productId", nativeQuery = true)
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> findByProductIdJoinProduct(@Param("productId") int productId);

    @Query("select o from Option o join fetch o.product p where o.id in (:ids)")
    List<Option> findByIdIn(List<Integer> ids);
}
