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
    "join fetch o.product p " +
    "where c.user.id = :userId")
  List<Cart> findAllByUserId(int userId);

  @Modifying
  @Query("update Cart c set c.quantity = :quantity where c.id = :id")
  void updateQuantityById(int id, int quantity);


  void deleteById(int id);

  @Modifying
  @Query("delete from Cart c where c.user.id = :userId")
  void deleteAllByUserId(@Param("userId") int userId);
}
