package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
	// saveOrder에 사용될 한방 쿼리 - Cart가 Option을 Lazy로딩하여 N+1문제 발생
	@Query("SELECT c FROM Cart c JOIN FETCH c.option o JOIN FETCH o.product WHERE c.user.id = :userId")
	List<Cart> findAllByUserId(@Param("userId") int userId);

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(@Param("userId") int userId);

    void deleteByUserId(int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
