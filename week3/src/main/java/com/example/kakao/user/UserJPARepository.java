package com.example.kakao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.username = :username")
//    Optional<User> findByUsername(String username);
    User findByUsername(@Param("username") String username);


}
