package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    @Query("select o from Option o " +
            "where o.product.id = :productId")
    List<Option> mFindByProductId(@Param("productId") int productId);

}