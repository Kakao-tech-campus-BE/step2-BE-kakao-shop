package com.example.kakaoboardjpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // JPA가 제공하는 namedQuery
    // 파라메터가 두개 이상이면 @Param은 필수이다. 한개일때는 생략가능하다.
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    // 직접 JPQL 객체지향 쿼리를 작성할 수 있다.
    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> jpqlFindByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    // native query를 사용하는 방법이다.
    @Query(value = "select * from user_tb where username = :username and password = :password", nativeQuery = true)
    Optional<User> nativeFindByUsernameAndPassword(@Param("username") String username, @Param("password") String password);


}
