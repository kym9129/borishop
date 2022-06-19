package com.borishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BorishopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorishopApplication.class, args);
	}

}
