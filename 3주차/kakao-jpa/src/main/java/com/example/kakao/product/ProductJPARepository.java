package com.example.kakao.product;

import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {

    //nameQuery
    Optional<Product> findByProductId(@Param("productId")String productId);



    //직접 JPQL 객체지향 쿼리 작성
    @Query("select p from Product p where p.id=:productId")
    Optional<User>jpqlFindByEmail(@Param("email")String email);

    // findById_select_product_lazy_error_fix_test
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> mFindByProductId(@Param("productId")int productId);

}

