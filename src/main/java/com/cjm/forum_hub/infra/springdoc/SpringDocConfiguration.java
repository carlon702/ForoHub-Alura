package com.cjm.forum_hub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Forum-Hub")
                        .description("Web API that simulates an student forum")
                        .contact(new Contact()
                                .name("Carlos Jose Marchal")
                                .email("carlosj.marchal@gmail.com"))
                )
                .components(
                        new Components()
                                .addSecuritySchemes("bearer-key",
                                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}
