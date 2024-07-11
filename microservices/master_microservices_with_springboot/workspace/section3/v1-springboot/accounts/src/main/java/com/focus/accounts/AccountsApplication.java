package com.focus.accounts;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import com.focus.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice",
				description = "EasyBank Accounts Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Dario Vinueza",
						email = "darvinnueza@gmail.com",
						url = "https://www.facebook.com/darvinueza"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.facebook.com/darvinueza"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EasyBank Accounts Microservice REST API Documentation",
				url = "https://github.com/darvinueza/back_demos/tree/master/MASTER_MICROSERVICES_WITH_SPRINGBOOT_DOCKER_KUBERNETES"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}
}
