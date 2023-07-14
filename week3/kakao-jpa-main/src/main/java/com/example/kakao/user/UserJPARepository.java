package com.example.kakao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> jpqlFindByUsernameAndPasword(@Param("username") String username, @Param("password") String password);

    @Query(value = "select * from user_tb where username = :username and password = :password", nativeQuery = true)
    Optional<User> nativeFindByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
