package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN FETCH c.option o JOIN FETCH o.product p WHERE c.user.id=:userId")
    List<Cart> findAllByUserId(int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.option o JOIN FETCH o.product p WHERE c.user.id=:userId")
    List<Cart> findAllByUserIdFetchOptionAndProduct(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);


    @Query("select c from Cart c join fetch c.option o where (c.option.id in :optionIds) and c.user.id = :userId")
    List<Cart> findByOptionIdInAndUserId(@Param("optionIds") List<Integer> optionIds, @Param("userId") int userId);
}
