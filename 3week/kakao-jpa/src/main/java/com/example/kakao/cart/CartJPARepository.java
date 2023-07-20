package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {


 /*   @Query(value = "select c.id, c.option_id, c.price, c.quantity, c.user_id," +
            "u.id, u.email, u.password, u.roles, u.username," +
            "o.id, o.product_id, o.option_name, o.price " +
            "from cart_tb c" +
            "left join user_tb u on u.id = c.user_id and c.user_id = :userId " +
            "left join option_tb o on o.id = c.option_id", nativeQuery = true)
    List<Cart> nativeFindByUserId(@Param("userId") int userId);*/

    @Query("select c from Cart c join fetch c.option where c.user.id = :userId")
    List<Cart> mFindByUserIdJoinFetchOption(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(@Param("userId") int userId);

    @Query("select c from Cart c join fetch c.option where c.id in :ids")
    List<Cart> mFindAllByIdJoinFetch(@Param("ids") List<Integer> ids);
}
