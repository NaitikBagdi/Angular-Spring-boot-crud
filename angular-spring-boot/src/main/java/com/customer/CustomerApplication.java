package com.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class that initializes and runs the Spring Boot application for
 * managing customers
 * 
 * @author Naitik Bagdi
 */
@SpringBootApplication
public class CustomerApplication {

	/**
	 * The main method of the application.
	 * 
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}