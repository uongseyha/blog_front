package edu.mum.cs544;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Main {

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}


	public static void main(String[] args) {

		//SpringApplication.run(Client.class, args);
		//System.setProperty("server.servlet.context-path", "/login");
		SpringApplication.run(Main.class, args);


	}

}
