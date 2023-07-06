package com.example.kakaoshop.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJPARepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}