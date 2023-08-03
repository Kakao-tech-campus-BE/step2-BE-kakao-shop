package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.option o join fetch o.product where c.user.id = :userId")
    List<Cart> findAllByUserId(@Param("userId") int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.option o JOIN FETCH o.product p WHERE c.user.id = :userId")
    List<Cart> findCartByUserId(@Param("userId") int userId);

    @Query("select c from Cart c where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    //@Query("SELECT c FROM Cart c JOIN FETCH c.option o JOIN FETCH c.user u WHERE c.user.id = :userId ORDER BY c.option.id ASC")
    //List<Cart> findWithOptionAndUserByUserIdOrderByOptionIdAsc(@Param("userId") int userId);
    //메모리 먹는거 때매 더 느릴듯..

    @Modifying
    @Transactional
    @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = :quantity, c.price = :price WHERE c.id = :id")
    void updateQuantityAndPriceById(@Param("id") int id, @Param("quantity") int quantity, @Param("price") int price);


    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}
