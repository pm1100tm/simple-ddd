package com.post.prac.framework.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
	private static final String TITLE = "My Study App API";
	private static final String DESCRIPTION = "API Documentation";
	private static final String VERSION = "1.0";
	private static final String URL = "http://localhost:9080";

	@Bean
	public OpenAPI customOpenAPI() {
		SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")
				.description("Bearer 을 제외한 토큰")
				.in(SecurityScheme.In.HEADER)
				.name("Authorization");
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI()
				.info(new Info()
						.title(TITLE)
						.description(DESCRIPTION)
						.version(VERSION)
						.license(new License().name("Apache 2.0").url(URL))
				)
				.components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
				.security(List.of(securityRequirement));
	}
}
