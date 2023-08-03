package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserId(int userId);
    Optional<Cart> findByUserId(int userid);

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    void deleteByUserId(int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);

    void deleteAllByUserId(int userId);
    @Query("select c from Cart c join fetch c.user u join fetch c.option o join fetch o.product where c.user.id = :userId")
    List<Cart> mFindAllByUserId(@Param("userId") int userId);

    @Query("select c from Cart c  join fetch c.option o join fetch o.product p where c.id = :cartId")
    Optional<Cart> mfindById(@Param("cartId") int cartId);

    @Modifying
    @Query("update Cart c set c.quantity = :quantity where c.id = :cartId")
    void updateQuantityById(@Param("cartId") int cartId,@Param("quantity") int quantity);
}
