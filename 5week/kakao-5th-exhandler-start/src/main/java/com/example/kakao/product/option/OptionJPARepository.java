package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    // 자동으로 작동하는 name query임
    // name query는 다 공부해야함 복잡해짐 // 쿼리를 쓰는 것을 추천함
    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);

    // findById_select_product_lazy_error_fix_test
    // 위 처럼 적을 수도 있음
    // @Query(value = "select * from option o inner join product p on o.product_id = p.id where o.product_id = :productId", nativeQuery = true)
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> findByProductIdJoinProduct(@Param("productId") int productId);
} // 위 2가지 쿼리를 비교해보자
// 첫 번째 쿼리 select 2개로 끝남

// 터미널 비우고 두 번째 쿼리 실행
// 조인으로 한방에 끝냄

// select 2번 하는 것이 좋다! v1!