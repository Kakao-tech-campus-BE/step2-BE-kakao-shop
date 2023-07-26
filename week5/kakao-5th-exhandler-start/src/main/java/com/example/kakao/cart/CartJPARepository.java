package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    //user의 카트 세부사항을 조회하는 쿼리. user id로 select해서 올 때, api 상 user정보는 필요하지 않음. 이럴 때, join을 사용해서 user부분은 ?로 남겨두고 가져온다. 3중 조인을 통해 option까지 호출
    @Query("select c from Cart c join fetch Option o join User where c.user.id = :userId order by o.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(@Param("userId") int userId);

    //user id로 조회하는 쿼리. cart자체는 cartid, optionid, 수량, 가격만 담겨있음. 그러므로 userid는 필요하지 않을 것 같다.
    @Query("select c from Cart c join User where c.user.id = :userId")
    List<Cart> findAllByUserId(@Param("userId") int userId);


    @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(@Param("userId") int userId);

    //option id와 user id를 동시에 활용하는 쿼리. user의 카트에 option이 실제 존재하는지 더티체킹하는 쿼리
    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
