package com.example.kakao.product.option;

import com.example.kakao.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    List<Option> findByProductId(@Param("productId") int productId);
    Optional<Option> findById(int id);

    // findById_select_product_lazy_error_fix_test
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> mFindByProductId(@Param("productId") int productId);

    //단일 엔티티에만 접근하기 위해 fetch 조인 사용하지 않음
    @Query("SELECT o FROM Option o WHERE o.product = :product AND o.optionName = :optionName")
    Option findByProductAndOptionName(@Param("product") Product product, @Param("optionName") String optionName);

    //Option을 조회하되 Product 내의 타옵션은 조회되지 않음
    @Query("SELECT o FROM Option o JOIN FETCH o.product WHERE o.id = :id")
    Option findWithProductById(@Param("id") Integer id);

}
