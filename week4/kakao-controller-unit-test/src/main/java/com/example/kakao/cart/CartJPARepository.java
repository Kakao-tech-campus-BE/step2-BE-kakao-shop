package com.example.kakao.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @EntityGraph(value="cartWithOptionProduct")
    List<Cart> findByUserId(int userId);

    @Query("select c from Cart c " +
            "join fetch c.option o " +
            "where c.user.id = :userId and c.option.id in (:optionIds)")
    List<Cart> findByUserIdAndOptionIdIn(@Param("optionIds") List<Integer> optionIds,
                                         @Param("userId") int userId);

    @EntityGraph(value="cartWithOption")
    List<Cart> findByIdIn(List<Integer> cartId);
}
