package com.claro.vmmanager.infra.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Virtual Machine Manager")
                        .description("Documentação da API com Swagger no Spring Boot")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Vitor Freitas")
                                .email("Developer.vitord@gmail.com")
                                .url("https://www.linkedin.com/in/vitor-dias-5450b5194/")
                        ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Informe apenas o token JWT, sem o prefixo 'Bearer'. Value: eyJhbGciO...")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}