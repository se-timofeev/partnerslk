package ru.planetnails.partnerslk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PartnersLkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnersLkApplication.class, args);
	}

}
