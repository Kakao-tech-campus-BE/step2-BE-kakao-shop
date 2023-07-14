package com.example.kakaoshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KakaoshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaoshopApplication.class, args);
	}

}
