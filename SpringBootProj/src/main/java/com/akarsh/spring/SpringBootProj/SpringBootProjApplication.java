package com.akarsh.spring.SpringBootProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.akarsh.spring.SpringBootProj.Repositories")

public class SpringBootProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjApplication.class, args);
	}

}

