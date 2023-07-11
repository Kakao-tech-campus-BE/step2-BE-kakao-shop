package com.example.kakaoshop.domain.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false, unique = true)
    private String email; // 인증시 필요한 필드
    @Column(length = 256, nullable = false)
    private String password;
    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 30)
    private String roles; // role은 한 개 이상
    // , 로 구분되는 일종의 csv 형식으로 저장하는 것 같음

    // ROLE NameSpace
    public static class Role {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String SELLER = "ROLE_SELLER";
    }

    @Builder
    public Account(int id, String email, String password, String username, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }
}
