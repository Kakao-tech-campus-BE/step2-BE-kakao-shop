package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.user u join fetch c.option o join fetch o.product where c.user.id = :userId order by c.option.id")
    List<Cart> findByUserIdOrderByOptionId(int userId);

    @Query("select c from Cart c join fetch c.option o where c.user.id = :userId")
    List<Cart> mFindAllByUserId(int userId);

    @Modifying(clearAutomatically = false)
    @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
