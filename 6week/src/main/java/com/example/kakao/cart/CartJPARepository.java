package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId")
    List<Cart> findAllByUserId(@Param("userId") int userId);
    
    @Query("select c from Cart c join fetch c.user u join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(@Param("userId") int userId);

    @Query("select c from Cart c where c.user.id = :userId and c.option.id = :optionId")
    Optional<Cart> findByUserIdAndOptionId(@Param("userId") int userId, @Param("optionId") int optionId);
}
