package com.numberguessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumberGuessingGameApplication {

	public static void main(String[] args) {
		Database.createTable();
		SpringApplication.run(NumberGuessingGameApplication.class, args);
	}

}
