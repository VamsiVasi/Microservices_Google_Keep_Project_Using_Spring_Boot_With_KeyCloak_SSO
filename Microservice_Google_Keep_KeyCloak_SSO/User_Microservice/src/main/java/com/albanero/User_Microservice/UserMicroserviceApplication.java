package com.albanero.User_Microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Bean
	public WebClient.Builder getWebClient()
	{
		return WebClient.builder();
		
	}

}