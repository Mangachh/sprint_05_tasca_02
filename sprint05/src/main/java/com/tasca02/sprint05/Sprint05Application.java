package com.tasca02.sprint05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class Sprint05Application {

	public static void main(String[] args) {
		SpringApplication.run(Sprint05Application.class, args);
	}

}
