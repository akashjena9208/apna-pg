package com.apnapg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class ApnaPgBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApnaPgBackendApplication.class, args);
	}

}
