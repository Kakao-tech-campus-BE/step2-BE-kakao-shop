package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c from Cart c join fetch c.user join fetch c.option o join fetch o.product where c.user.id = :userId")
    List<Cart> mFindAllByUserId(@Param("userId") int userId);

    @Modifying
    @Query("delete from Cart c where c.id in :ids")
    void deleteAllById(@Param("ids") List<Integer> cartIds);
}
