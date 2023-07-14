package com.example.kakaoboardjpa.user;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_tb")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20, nullable = false, unique = true)
    private String username;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    private String email;
}

