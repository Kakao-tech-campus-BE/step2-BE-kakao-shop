package com.example.kakao.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogJPARepository extends JpaRepository<ErrorLog, Integer> {
}
