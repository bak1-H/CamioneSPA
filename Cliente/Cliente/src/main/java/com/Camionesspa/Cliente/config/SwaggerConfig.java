package com.Camionesspa.Cliente.config;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cliente - CAMIONES SPA")
                        .version("1.0.0")
                        .description("API de gestión de clientes para CAMIONES SPA"));
    }
}