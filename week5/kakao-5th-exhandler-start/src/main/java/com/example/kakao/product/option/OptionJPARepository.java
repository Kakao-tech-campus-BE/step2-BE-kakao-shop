package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    //option과 함께 product 정보들도 포함시켜야 하기 때문에 join fetch 전략을 사용한다.
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);
}
