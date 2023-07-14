package com.example.kakao.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int userId);

    Optional<Cart> findByUserIdAndOptionId(int userId, int optionId);

    @Query("SELECT c FROM Cart c JOIN fetch c.option o JOIN fetch c.user u JOIN fetch o.product p WHERE c.user.id = :userId")
    List<Cart> findAllCartsWithOptionsAndUserAndProductByUserId(@Param("userId") int userId);

    @EntityGraph(attributePaths = {"option", "user", "option.product"})
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findAllCartsWithOptionsAndUserAndProductByUserIdWithEntityGraph(@Param("userId") int userId);

}
