package com.mongoreact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@EnableCaching
public class ReactiveMongoDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongoDemoApplication.class, args);
	}

}
