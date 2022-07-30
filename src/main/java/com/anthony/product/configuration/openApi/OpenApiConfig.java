package com.anthony.product.configuration.openApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    //public static final String AUTHORIZATION_HEADER = "Authorization";
    @Bean
    public OpenAPI openApi() {

       /* Server localServer = new Server();
        localServer.setDescription("local");
        localServer.setUrl("http://localhost:8080");

        Server testServer = new Server();
        testServer.setDescription("test");
        testServer.setUrl("https://example.org");*/

        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
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
        //openAPI.setServers(Arrays.asList(localServer, testServer));

        return openAPI;
    }

}