package com.example.kakaoshop.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User {

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

  @Builder
  public User(int id, String email, String password, String username, String roles) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.username = username;
    this.roles = roles;
  }
}
