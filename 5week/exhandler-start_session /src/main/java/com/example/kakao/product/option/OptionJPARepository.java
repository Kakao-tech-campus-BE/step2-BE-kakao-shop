package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {
    //join하지 않은거
    //쿼리 리펙토링 1차 - join 하지 않음
    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);
    //직접 join한 것 위에 코드와 비교 - 이게 좋다
    //아래 쿼리를 다르게 쓰는 방법
    //@Query(value = "select * from option o inner join product p on o.product_id = p.id where o.product_id = :productId", nativeQuery = true)
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> findByProductIdJoinProduct(@Param("productId") int productId);

    Optional<Option> findById(int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> FindByProductId(@Param("productId") int productId);


}
