package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(int userId);

    @Query("select c from Cart c join fetch c.option o where (c.option.id in :optionIds) and c.user.id = :userId")
    List<Cart> findByOptionIdInAndUserId(@Param("optionIds") List<Integer> optionIds, @Param("userId") int userId);
}
