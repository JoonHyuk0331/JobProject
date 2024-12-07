package com.example.jobproject.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    String accessTokenKey = "Access Token (Bearer)";
    String refreshTokenKey = "Refresh Token";

    SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList(accessTokenKey)
            .addList(refreshTokenKey);

    // Define the Access Token security scheme (Bearer Token)
    SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

    // Define the Refresh Token security scheme (API Key style)
    SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .in(SecurityScheme.In.HEADER)
            .name("Refresh-Token");

    Components components = new Components()
            .addSecuritySchemes(accessTokenKey, accessTokenSecurityScheme)
            .addSecuritySchemes(refreshTokenKey, refreshTokenSecurityScheme);

    private Info apilnfo() {
        return new Info()
                .title("Job Project")
                .description("채용공고 api 명세서")
                .version("1.0.0");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(components)
                .info(apilnfo());
    }
}
