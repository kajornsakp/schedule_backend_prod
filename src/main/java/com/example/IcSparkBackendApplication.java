package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;


@EnableAutoConfiguration
@SpringBootApplication
public class IcSparkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcSparkBackendApplication.class, args);

	}
}
