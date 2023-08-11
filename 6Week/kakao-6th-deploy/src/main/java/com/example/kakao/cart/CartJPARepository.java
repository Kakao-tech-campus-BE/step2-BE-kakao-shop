package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.option o join fetch o.product where c.user.id = :userId")
    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Cart findByOptionIdAndUserId(int optionId, int userId);

    @Modifying
    @Query("delete from Cart c where c.id in :ids")
    void deleteAllById(@Param("ids") List<Integer> cartIds);
}
