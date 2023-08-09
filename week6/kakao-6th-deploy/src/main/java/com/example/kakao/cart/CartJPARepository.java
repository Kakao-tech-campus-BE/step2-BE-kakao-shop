package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c " +
            "join fetch c.option o " +
            "where c.user.id = :userId")
    List<Cart> findAllByUserIdFetchOption(int userId);

    @Query("select c from Cart c " +
            "join fetch c.option o " +
            "join fetch o.product p " +
            "where c.user.id = :userId")
    List<Cart> findAllByUserIdFetchJoin(int userId);

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    @Query("select c from Cart c " +
            "join fetch c.option o " +
            "where o.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);

    @Query("select c from Cart c " +
            "join fetch c.option o " +
            "where c.user.id = :userId and c.option.id in (:optionIds)")
    List<Cart> findByUserIdAndOptionIdIn(@Param("optionIds") List<Integer> optionIds,
                                         @Param("userId") int userId);


    // 기존의 deleteAllInBatch의 경우 or로 삭제할 데이터를 묶지만, in으로 처리하는 것이 더 빠르다.
    // 이는 or과 in의 쿼리 실행계획이 다르기 때문.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Cart c " +
            "where c.id in(:cartIds)")
    void deleteAllIn(List<Integer> cartIds);

}
