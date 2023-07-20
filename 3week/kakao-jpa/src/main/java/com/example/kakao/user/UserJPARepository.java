package com.example.kakao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.username = :username, u.password = :password WHERE u.email = :email")
    int updateUserByEmail(@Param("email") String email, @Param("username") String username, @Param("password") String password);
}
