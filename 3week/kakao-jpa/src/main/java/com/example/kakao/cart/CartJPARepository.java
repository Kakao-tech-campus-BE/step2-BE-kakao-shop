package com.example.kakao.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    Cart findByUserId(int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.user u JOIN FETCH c.option o WHERE u.id = :userId AND o.id = :optionId")
    Cart findByUserAndOption(@Param("userId") int userId, @Param("optionId") int optionId);

    //join fetch 방식도 같이 써보았습니다.
    //@Query("SELECT c FROM Cart c JOIN fetch c.option o JOIN fetch c.user u JOIN fetch o.product p WHERE c.user.id = :userId")
    @EntityGraph(attributePaths = {"option", "user", "option.product"})
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findAllCartByUserId(@Param("userId") int userId);
}
