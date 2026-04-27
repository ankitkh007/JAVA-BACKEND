package com.ankit.week_5_relationships;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Week5RelationshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Week5RelationshipsApplication.class, args);
	}

}
