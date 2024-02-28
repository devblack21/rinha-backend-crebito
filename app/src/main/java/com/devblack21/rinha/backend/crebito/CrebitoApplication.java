package com.devblack21.rinha.backend.crebito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableJpaRepositories
@SpringBootApplication
@EnableTransactionManagement
public class CrebitoApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CrebitoApplication.class, args);
	}
}
