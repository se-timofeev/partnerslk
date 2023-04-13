package ru.planetnails.partnerslk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(info = @Info(
        title = "Partners account API",
        version = "0.1",
        description = "Partners account API description"))
public class PartnersLkApplication {

    public static void main(String[] args) {
               SpringApplication.run(PartnersLkApplication.class, args);


    }

}
