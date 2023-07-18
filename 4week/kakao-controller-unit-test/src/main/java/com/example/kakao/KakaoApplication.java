package com.example.kakao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@SpringBootApplication
public class KakaoApplication {



	public static void main(String[] args) {
		SpringApplication.run(KakaoApplication.class, args);
	}

}
