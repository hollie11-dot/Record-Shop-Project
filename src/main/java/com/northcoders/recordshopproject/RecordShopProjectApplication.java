package com.northcoders.recordshopproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecordShopProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordShopProjectApplication.class, args);
	}

}
