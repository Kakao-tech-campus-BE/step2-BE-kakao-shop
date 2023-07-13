package com.example.kakaoshop.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
}
