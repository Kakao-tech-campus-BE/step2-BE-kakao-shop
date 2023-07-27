package com.example.kakao.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @EntityGraph(attributePaths = "option")
    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

//    void deleteByUserId(int userId);

    List<Cart> findByOptionIdInAndUserId(Set<Integer> optionIds, Integer userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);

    //위와 같은 api 에서 사용할 예정이지만 boolean 타입으로 결과를 도출하는 쿼리를 작성해보고 싶었다.
    @Query("select case when count(c)>0 then true else false end from Cart c where c.option.id = :optionId and c.user.id = :userId")
    boolean existsByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
