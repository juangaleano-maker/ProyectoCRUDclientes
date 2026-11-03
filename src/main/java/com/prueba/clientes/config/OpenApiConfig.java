package com.prueba.clientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Clientes")
                        .version("1.0.0")
                        .description("CRUD de clientes - Prueba Técnica")
                        .contact(new Contact()
                                .name("Desarrollador")
                                .email("dev@example.com")));
    }
}
