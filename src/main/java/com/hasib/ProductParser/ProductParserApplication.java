package com.hasib.ProductParser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductParserApplication.class, args);
	}

}
