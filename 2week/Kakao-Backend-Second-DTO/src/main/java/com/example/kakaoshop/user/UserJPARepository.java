package com.example.kakaoshop.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}