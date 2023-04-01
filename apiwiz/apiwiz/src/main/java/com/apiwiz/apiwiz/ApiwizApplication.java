package com.apiwiz.apiwiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import com.apiwiz.apiwiz.Dtos.LoginDto;


@SpringBootApplication
@EnableScheduling
public class ApiwizApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiwizApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}





}
