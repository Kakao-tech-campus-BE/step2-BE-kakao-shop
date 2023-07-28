package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    // n+1 문제 발생 -> cart 를 select 하고 option 과 product 를 한번 더 select 하는 문제를 해결하기 위한 한방 쿼리 작성
    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId")
    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    void deleteByUserId(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);

    // 여기도 따로 쿼리를 작성해줄 필요가 있는지?
    @Modifying
    @Query("delete from Cart c where c.user.id = :id")
    void deleteAllByUserId(int id);
}
