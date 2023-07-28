package com.example.kakao.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogJPARepository extends JpaRepository<ErrorLog, Integer> {
}
