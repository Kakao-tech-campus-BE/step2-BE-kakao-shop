package com.example.kakao.cart;

import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(int userId);

    Cart findByUserIdAndOption_Id(@Param("userId")int user_id,@Param("optionId") int option_id);

    Optional<Cart> deleteByUserId(@Param("userId")int user_id);

}
