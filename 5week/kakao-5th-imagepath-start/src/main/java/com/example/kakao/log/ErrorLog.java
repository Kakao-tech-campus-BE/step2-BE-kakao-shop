package com.example.kakao.log;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="error_log_tb")
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private Integer userId;
    @Column(nullable = false)
    private String userIp;
    @Column(nullable = false)
    private String userAgent;
    @Column(nullable = false, length = 1000)
    private String message;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }

    @Builder
    public ErrorLog(int id, Integer userId, String userIp, String userAgent, String message, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userIp = userIp;
        this.userAgent = userAgent;
        this.message = message;
        this.createdAt = createdAt;
    }
}
