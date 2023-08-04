package com.example.kakao.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findByProductId(@Param("productId") int productId);


    @Query("select o from Option o join fetch o.product where o.id = :optionId")
    Optional<Option> findByIdJoinProduct(@Param("optionId") int optionId);

    @Query("select o from Option o join fetch o.product where o.id in :optionIds")
    List<Option> findAllByIdJoinProduct(@Param("optionIds") List<Integer> optionIds);

    // Option이 fk를 가지고 있으므로 driving table이 되어야함.
    @Query("select o from Option o join fetch o.product where o.product.id = :productId")
    List<Option> findByProductIdJoinProduct(@Param("productId") int productId);
}
