package com.anthony.product.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    //public static final String AUTHORIZATION_HEADER = "Authorization";
/*
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nautilus.appconsumerauthbff.controller"))
                .paths(PathSelectors.any())
                .build()
                .forCodeGeneration(true);
    }
    private ApiInfo getApiInfo() {
        return  new Info().title("Product-Microservice")
                .description("This is a sample Spring Boot RESTFul service using springdoc-openapi and OpenAPI 3.")
                .version("1.0")
                .license(new License().name("Apache 2.0")
                        .url("https://springdoc.org"));
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }*/

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product-Microservice")
                        .description("This is a sample Spring Boot RESTFul service using springdoc-openapi and OpenAPI 3.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Arun")
                                .url("https://asbnotebook.com")
                                .email("asbnotebook@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                );
    }

}
