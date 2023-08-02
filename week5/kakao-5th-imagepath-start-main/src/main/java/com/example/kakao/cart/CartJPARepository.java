package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c join fetch c.option where c.user.id = :userId")
    List<Cart> findAllByUserIdJoinFetch(@Param("userId") int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);

    @Query("select c from Cart c join fetch c.option where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserIdJoinFetch(@Param("optionId") int optionId, @Param("userId") int userId);

    @Query("select c from Cart c where c.id = :cartId and c.user.id = :userId")
    Optional<Cart> findByCartIdAndUserId(@Param("cartId") int cartId, @Param("userId") int userId);

    @Modifying
    @Query("update Cart c set c.quantity = :#{#cart.quantity}, c.price = :#{#cart.price} where c.id = :#{#cart.id}")
    void update(@Param("cart") Cart cart);

    void deleteByUserId(@Param("userId") int userId);
}
