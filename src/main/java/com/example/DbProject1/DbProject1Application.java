package com.example.DbProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * This is a driver class that starts the application. 
 * It makes a tomcat sever to host the application on the port number that 
 * is mentioned in application.properties in src/main/resources folder.
 *
 */
@SpringBootApplication
public class DbProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(DbProject1Application.class, args);
	}

}
