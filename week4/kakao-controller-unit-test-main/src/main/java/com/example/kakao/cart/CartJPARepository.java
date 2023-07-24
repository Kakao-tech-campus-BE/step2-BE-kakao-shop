package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @Query("select c " +
            "from Cart c " +
            "join fetch c.option o " +
            "where c.user.id = :user_id and o.id in (:option_ids)")
    List<Cart> findDuplicatedCartsByOptionIds(@Param("user_id")int id, @Param("option_ids") List<Integer> ids);


    @Query("select c " +
            "from Cart c " +
            "join fetch c.option o " +
            "join fetch o.product " +
            "join fetch c.user u " +
            "where c.user.id = :userId")
    List<Cart> findCartByUserId(@Param("userId") int userId);

    @Modifying
    @Query("delete from Cart c " +
            "where c.id in (:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);

    @Modifying
    @Query("delete from Cart c " +
            "where c.user.id in :id")
    void deleteByUserId(@Param("id") int id);

    @Query("select count(*)" +
            "from Cart c " +
            "where c.user.id = :user_id")
    int countByUserId(@Param("user_id") int user_id);
}
