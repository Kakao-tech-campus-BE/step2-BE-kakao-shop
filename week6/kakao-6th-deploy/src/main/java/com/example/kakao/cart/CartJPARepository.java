package com.example.kakao.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    // @Query(value = "insert into cart_tb(user_id, option_id, quantity, price) values(:userId, :optionId, :quantity, :price)", nativeQuery = true)
    // void mSave(@Param("userId") int userId, @Param("optionId") int optionId, @Param("quantity") int quantity, @Param("price") int price);

    List<Cart> findAllByUserId(int userId);

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :userId order by c.option.id asc")
    List<Cart> findByUserIdOrderByOptionIdAsc(int userId);

    // @Query("delete from Cart c where c.user.id = :userId")
    void deleteByUserId(int userId);

    @Query("select c from Cart c where c.option.id = :optionId and c.user.id = :userId")
    Optional<Cart> findByOptionIdAndUserId(@Param("optionId") int optionId, @Param("userId") int userId);
}