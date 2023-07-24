package com.example.kakao.domain.product.option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OptionJPARepository extends JpaRepository<Option, Integer> {

    List<Option> findByProductId(int productId);
    Optional<Option> findById(int id);


    // 매우 간단한 기본 Query Method 가 아니라면, JPQL 을 직접 명시해주는것이 "옛날 사람"들과도 같이 일할 수 있다고 권장하셔서 남겨두었습니다.
    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findAllByProductId(int productId);
}
