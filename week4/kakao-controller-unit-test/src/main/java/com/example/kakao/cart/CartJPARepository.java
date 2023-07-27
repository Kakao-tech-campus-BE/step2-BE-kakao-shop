package com.example.kakao.cart;

import com.example.kakao.product.option.Option;
import com.example.kakao.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartJPARepository extends JpaRepository<Cart, Integer> {
    @EntityGraph("CartWithUserAndOption")
    List<Cart> findAll();

    @EntityGraph("CartWithUserAndOption")
    Optional<Cart> findById(int id);

    @EntityGraph("CartWithUserAndOption")
    List<Cart> findAllByUser(User user);
}
