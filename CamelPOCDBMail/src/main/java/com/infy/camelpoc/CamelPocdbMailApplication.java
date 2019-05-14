package com.infy.camelpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@EnableAutoConfiguration
public class CamelPocdbMailApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CamelPocdbMailApplication.class, args);
	}

}
