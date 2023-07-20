package com.example.kakao.cart;

import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(@Param("userId") int userId);

    @Query("select c from Cart c join fetch c.user where c.user.id = :userId")
     List<Cart> mfindByUserId(@Param("userId") int userId);


}
